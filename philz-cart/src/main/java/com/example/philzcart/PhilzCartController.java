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
    private List<PhilzProduct> productList; 

    /**
     * Message Object
     */
    class Message {
        @Getter
        @Setter
        private String status;
    }

    private HashMap<String, PhilzCart> order = new HashMap<>(); // String - id, PhilzOrder - order itself
    private HashMap<String, ArrayList<PhilzProduct>> cartItems; 

    PhilzCartController(PhilzProductRepository productRepository, PhilzCartRepository repository) {
        this.productRepository = productRepository;
        this.repository = repository;
        this.cartItems = new HashMap<String, ArrayList<PhilzProduct>>(); 

        this.productList = new ArrayList<PhilzProduct>(); 
        this.productList.add(new PhilzProduct("JACOBS", "dark")); 
        this.productList.add(new PhilzProduct("ARABIC", "dark")); 
        this.productList.add(new PhilzProduct("ECSTATIC", "dark")); 
        this.productList.add(new PhilzProduct("ETHER", "dark")); 
        this.productList.add(new PhilzProduct("TURKISH", "dark")); 
        this.productList.add(new PhilzProduct("GRATITUDE", "medium")); 
        this.productList.add(new PhilzProduct("TESORA", "medium")); 
        this.productList.add(new PhilzProduct("ALARM", "medium")); 
        this.productList.add(new PhilzProduct("JULIES", "medium")); 
        this.productList.add(new PhilzProduct("PHILHARMONIC", "medium")); 
        this.productList.add(new PhilzProduct("PHILTEREDSOUL", "medium")); 
        this.productList.add(new PhilzProduct("SILKEN", "medium")); 
        this.productList.add(new PhilzProduct("AMBROSIA", "light")); 
        this.productList.add(new PhilzProduct("MANHATTAN", "light")); 
        this.productList.add(new PhilzProduct("COLUMBIA", "decaf")); 
        this.productList.add(new PhilzProduct("ETHIOPIA", "decaf")); 
        this.productList.add(new PhilzProduct("HOUSE", "decaf")); 

    }

//    api/products/{userid} @Get #every product will be on here. This is also the home page @Get

    /**
     * @GetMapping("/product")
        Iterable<PhilzProducts> getAllOrders() {
        return this.productRepository.findAll();
    }*/

    /**
     * Gets all the available product types to render. 
     */
    @GetMapping("api/products")
    List<PhilzProduct> getAllProductTypes(){
        return this.productList; 
    }

    /** api/cart/{userid}
     *  Get a person's individual cart
     * @param email the individual who's cart we're looking for
     * @param response Httpservlet response error handling?
     * @return returns a list of all items in the user's cart
     */
    @GetMapping("api/cart/{email}")
    List<PhilzProduct> getIndividualCart(@PathVariable String email, HttpServletResponse response) {
       
        //Do some verification check that this is that person's cart and not someone else's

        List<PhilzProduct> cart = this.cartItems.get(email);
        if (cart != null) {
            return cart;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No items in cart! Add more to cart");
        }
    }


    /**
     * Adding a new item to a specific person's cart
     * @param email the person 
     * @param item the cart item to add
     * @param response http response
     */
    @PostMapping("api/cart/{email}/{item}")
    void addItemToCart(@PathVariable String email, @PathVariable PhilzProduct item, HttpServletResponse response){
        this.cartItems.get(email).add(item); 
    }

//    api/cart/{userid}
//    api/cart/{userid} @Delete #delete entire cart

//    // Create new order
    @PostMapping("api/cart/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    PhilzCart newOrder(@PathVariable String userid, @RequestBody PhilzProducts coffee) {

        System.out.println("Creating order " + userid + ": " + coffee);

        PhilzProducts product = productRepository.findAllByName(coffee.name);

        PhilzCart active = repository.findByUserId(userid);
        if (active == null) {
            active = new PhilzCart();
//            System.out.println("Active order " + userid + ": " + coffee);
//            if (active.getStatus().equals("Ready for Payment")) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Already Exists!");
//            }
        }

        double total = product.getPrice() * 0.0725;
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total + scale) / scale;
        double running_total = 0.0;
        if (active != null) {
            running_total = active.getTotal() + rounded;
        } else {
            running_total = rounded;
        }

        active.setStatus(Status.IN_PROGRESS);
        active.addProduct(product);
        active.setTotal(running_total);
        repository.save(active);
        order.put(userid, active);

        return active;
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
