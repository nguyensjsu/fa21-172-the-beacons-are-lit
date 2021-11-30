package com.example.philzapidb.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.philzapidb.enums.order.CreamLevel;
import com.example.philzapidb.enums.order.DrinkType;
import com.example.philzapidb.enums.order.MilkType;
import com.example.philzapidb.enums.order.Ordersize;
import com.example.philzapidb.enums.order.SugarAmnt;
import com.example.philzapidb.enums.order.TempLevel;

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
public class PhilzOrderController {

    private final PhilzOrderRepository repository;

    @Autowired
    private PhilzCardRepository cardsRepository;

    class Message {
        @Getter
        @Setter
        private String status;
    }

    private HashMap<String, PhilzOrder> orders = new HashMap<>(); // String - id, StarbucksOrder - order itself

    PhilzOrderController(PhilzOrderRepository repository) {
        this.repository = repository;
    }

    // Create new order
    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    PhilzOrder newOrder(@PathVariable String regid, @RequestBody PhilzOrder order) {

        System.out.println("Creating order " + regid + ": " + order);

        if (order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request");
        }

        PhilzOrder active = this.orders.get(regid);
        if (active != null) {
            System.out.println("Active order " + regid + ": " + order);
            if (active.getStatus().equals("Ready for Payment")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Exists!");
            }
        }

        double total = drinkPrice(order.getDrink(), order.getSize());
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total + scale) / scale;
        order.setTotal(rounded);

        order.setStatus("Ready for Payment");
        PhilzOrder newOrder = this.repository.save(order);
        this.orders.put(regid, newOrder); // Add to the hashmap

        return newOrder;
    }

    /**
     * Private, used by new order. 
     * Determines the pricing for the drink including tax
     * @param drink Drink type
     * @param size Size of the drink
     * @return Price for the drink including tax
     */
    private double drinkPrice(String drink, String size){
        double price = 0.0;
        switch (drink) {
            case "JACOBS":
            case "ETHER":
            case "ARABIC":
            case "TURKISH":
            case "TESORA":
            case "PHILHARMONIC":
            case "JULIES":
            case "SOUL":
            case "SILKEN":
            case "ALARM":
            case "GRATITUDE":
            case "AMBROSIA":
            case "DANCINGWATER":
            case "NEWMANHATTAN":
            case "COLUMBIADECAF":
            case "MEDIUMDECAF":
            case "LIGHTDECAF":
            case "ECSTATIC":
                switch (size) {
                case "SMALL":
                    price = 3.85;
                    break;
                case "MEDIUM":
                    price = 4.70;
                    break;
                case "LARGE":
                    price = 5.35;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
                }
                break;
            case "MOCHA":
            case "WINTER":
            case "MOJITO":
            case "ROSE":
            case "GINGERSNAP":
            case "MISSIONCOLDBREW":
            case "SOULCOLDBREW":
                switch (size) {
                case "SMALL":
                    price = 4.85;
                    break;
                case "MEDIUM":
                    price = 5.70;
                    break;
                case "LARGE":
                    price = 6.35;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size!");
                }
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Drink!");
            }
            double tax = 0.0725;
            price = price + (price * tax);
        return price;
    }

    // Get info about specific active order
    @GetMapping("/order/register/{regid}")
    PhilzOrder getActiveOrder(@PathVariable String regid, HttpServletResponse response) {
        PhilzOrder active = this.orders.get(regid);
        if (active != null) {
            return active;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
    }

    // Clear a specific order
    @DeleteMapping("/order/register/{regid}")
    Message deleteActiveOrder(@PathVariable String regid) {
        PhilzOrder active = this.orders.get(regid);
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
    PhilzCard processOrder(@PathVariable String regid, @PathVariable String cardnum) {
        System.out.println("Pay for order " + regid + " with card: " + cardnum);

        PhilzOrder active = this.orders.get(regid);

        if (active == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
        if (cardnum.equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Number Not Provided!");
        }
        if (active.getStatus().startsWith("Paid with Card")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Cleared!");
        }

        PhilzCard card = this.cardsRepository.findByCardNumber(cardnum);
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
    List<PhilzOrder> getAllOrders() {
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
