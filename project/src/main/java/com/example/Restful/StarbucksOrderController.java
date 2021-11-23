package com.example.Restful;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
public class StarbucksOrderController {

    private final StarbucksOrderRepository repository;

    @Autowired
    private StarbucksCardRepository cardsRepository;

    class Message {
        @Getter
        @Setter
        private String status;
    }

    private HashMap<String, StarbucksOrder> orders = new HashMap<>(); // String - id, StarbucksOrder - order itself

    StarbucksOrderController(StarbucksOrderRepository repository) {
        this.repository = repository;
    }

    // Create new order
    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    StarbucksOrder newOrder(@PathVariable String regid, @RequestBody StarbucksOrder order) {

        System.out.println("Creating order " + regid + ": " + order);

        if (order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request");
        }

        StarbucksOrder active = this.orders.get(regid);
        if (active != null) {
            System.out.println("Active order " + regid + ": " + order);
            if (active.getStatus().equals("Ready for Payment")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Exists!");
            }
        }

        double price = 0.0;
        switch (order.getDrink()) {
        case "Latte":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.65;
                break;
            case "Venti":
            case "Your Own Cup":
                price = 3.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
            }
            break;
        case "Americano":
            switch (order.getSize()) {
            case "Tall":
                price = 2.25;
                break;
            case "Grande":
                price = 2.65;
                break;
            case "Venti":
            case "Your Own Cup":
                price = 3.25;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
            }
            break;
        case "Mocha":
            switch (order.getSize()) {
            case "Tall":
                price = 3.45;
                break;
            case "Grande":
                price = 4.15;
                break;
            case "Venti":
            case "Your Own Cup":
                price = 4.45;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
            }
            break;
        case "Espresso":
            switch (order.getSize()) {
            case "Tall":
                price = 1.95;
                break;
            case "Short":
                price = 1.75;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
            }
            break;
        case "Cappuccino":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.65;
                break;
            case "Venti":
            case "Your Own Cup":
                price = 3.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
            }
            break;
        default:
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Drink!");
        }
        double tax = 0.0725;
        double total = price + (price * tax);
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total + scale) / scale;
        order.setTotal(rounded);

        order.setStatus("Ready for Payment");
        StarbucksOrder newOrder = this.repository.save(order);
        this.orders.put(regid, newOrder); // Add to the hashmap

        return newOrder;
    }

    // Get info about specific active order
    @GetMapping("/order/register/{regid}")
    StarbucksOrder getActiveOrder(@PathVariable String regid, HttpServletResponse response) {
        StarbucksOrder active = this.orders.get(regid);
        if (active != null) {
            return active;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
    }

    // Clear a specific order
    @DeleteMapping("/order/register/{regid}")
    Message deleteActiveOrder(@PathVariable String regid) {
        StarbucksOrder active = this.orders.get(regid);
        if (active != null) {
            this.orders.remove(regid);
            Message msg = new Message();
            msg.setStatus("Active Order Cleared!");
            return msg;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
    }

    // Pay for active order
    @PostMapping("/order/register/{regid}/pay/{cardnum}")
    StarbucksCard processOrder(@PathVariable String regid, @PathVariable String cardnum) {
        System.out.println("Pay for order " + regid + " with card: " + cardnum);

        StarbucksOrder active = this.orders.get(regid);

        if (active == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
        if (cardnum.equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Number Not Provided!");
        }
        if (active.getStatus().startsWith("Paid with Card")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Cleared!");
        }

        StarbucksCard card = this.cardsRepository.findByCardNumber(cardnum);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Not Found!");
        }
        if (!card.isActivated()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Not Activated!");
        }

        double price = active.getTotal();
        double balance = card.getBalance();
        if (balance - price < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Fund on Card!");
        }
        double newBalance = balance - price;
        card.setBalance(newBalance);
        String status = "Paid with Card: " + cardnum + " Balance: $" + newBalance + ".";
        active.setStatus(status);
        this.cardsRepository.save(card);
        this.repository.save(active);
        return card;
    }

    // get all orders
    @GetMapping("/orders")
    List<StarbucksOrder> getAllOrders() {
        return this.repository.findAll();
    }

    // delete all orders
    @DeleteMapping("/orders")
    Message deleteAll() {
        this.repository.deleteAllInBatch();
        this.orders.clear();
        Message msg = new Message();
        msg.setStatus("All Orders Deleted!");
        return msg;
    }
}
