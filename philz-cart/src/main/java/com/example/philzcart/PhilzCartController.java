package com.example.philzcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
public class PhilzCartController {

    private final PhilzCartRepository repository;
    private final PhilzProductRepository productRepository;

    /**
     * Message Object
     */
    class Message {
        @Getter
        @Setter
        private String status;
    }

    private HashMap<String, PhilzCart> orders = new HashMap<>(); // String - id, PhilzOrder - order itself

    PhilzCartController(PhilzProductRepository productRepository, PhilzCartRepository repository) {
        this.productRepository = productRepository;
        this.repository = repository;
    }

//    api/products/{userid} @Get #every product will be on here. This is also the home page @Get

        // get all orders
    @GetMapping("/product")
    Iterable<PhilzProducts> getAllOrders() {
        return this.productRepository.findAll();
    }

    //    api/cart/{userid} @Get
    //Get info about specific active order
    @GetMapping("/cart/{userid}")
    PhilzCart getActiveOrder(@PathVariable String userid, HttpServletResponse response) {
        PhilzCart active = repository.findByUserId(userid);
        if (active != null) {
            return active;
        } else {
            PhilzCart cart = new PhilzCart();
            cart.setUserId(userid);
            repository.save(cart);
            return cart;
        }
    }

//    api/cart/{userid}
//    api/cart/{userid} @Delete #delete entire cart

//    // Create new order
    @PostMapping("/cart/{userid}")
    @ResponseStatus(HttpStatus.CREATED)
    PhilzCart newOrder(@PathVariable String userid, @RequestBody PhilzProducts coffee) {

        System.out.println("Creating order " + userid + ": " + coffee);

        PhilzProducts product = productRepository.findAllByName(coffee.name);

        PhilzCart active = repository.findByUserId(userid);
//        if (active != null) {
//            System.out.println("Active order " + userid + ": " + coffee);
//            if (active.getStatus().equals("Ready for Payment")) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Exists!");
//            }
//        }

        double total = product.getPrice() * 0.0725;
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total + scale) / scale;
        if (if (active == null) ){

        }
        double running_total = active.getTotal() + total;
        active.setTotal(running_total);

//        .setStatus("Ready for Payment.");
        PhilzCart newOrder = new PhilzCart();
        newOrder.setUserId(userid);
        newOrder.setOrder(product);
        newOrder.setStatus(Status.IN_PROGRESS);
        repository.save(newOrder);
        orders.put(userid, newOrder);

        return newOrder;
    }
//
//    /**
//     * Private, used by new order.
//     * Determines the pricing for the drink including tax
//     * @param drink Drink type
//     * @param size Size of the drink
//     * @return Price for the drink including tax
//     */

//
//    // Get info about specific active order
//    @GetMapping("/order/register/{regid}")
//    PhilzCart getActiveOrder(@PathVariable String regid, HttpServletResponse response) {
//        PhilzCart active = this.orders.get(regid);
//        if (active != null) {
//            return active;
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
//        }
//    }
//
//    // Clear a specific order
//    @DeleteMapping("/order/register/{regid}")
//    Message deleteActiveOrder(@PathVariable String regid) {
//        PhilzCart active = this.orders.get(regid);
//        if (active != null) {
//            this.orders.remove(regid);
//            Message msg = new Message();
//            msg.setStatus("Active Order Cleared!");
//            return msg;
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
//        }
//    }
//
//    // Pay for active order
//    @PostMapping("/order/register/{regid}/pay/{cardnum}")
//    PhilzCard processOrder(@PathVariable String regid, @PathVariable String cardnum) {
//        System.out.println("Pay for order " + regid + " with card: " + cardnum);
//
//        PhilzOrder active = this.orders.get(regid);
//
//        if (active == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
//        }
//        if (cardnum.equals("")) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Number Not Provided!");
//        }
//        if (active.getStatus().startsWith("Paid with Card")) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Cleared!");
//        }
//
//        PhilzCard card = this.cardsRepository.findByCardNumber(cardnum);
//        if (card == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Not Found!");
//        }
//        if (!card.isActivated()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Not Activated!");
//        }
//
//        double price = active.getTotal();
//        double balance = card.getBalance();
//        if (balance - price < 0) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Fund on Card!");
//        }
//        double newBalance = balance - price;
//        card.setBalance(newBalance);
//        String status = "Paid with Card: " + cardnum + " Balance: $" + newBalance + ".";
//        active.setStatus(status);
//        this.cardsRepository.save(card);
//        this.repository.save(active);
//        return card;
//    }
//

//
//    // delete all orders
//    @DeleteMapping("/orders")
//    Message deleteAll() {
//        this.repository.deleteAllInBatch();
//        this.orders.clear();
//        Message msg = new Message();
//        msg.setStatus("All Orders Deleted!");
//        return msg;
//    }
}
