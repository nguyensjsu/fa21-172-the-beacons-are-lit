package com.example.philzcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
public class PhilzCartController {

    @Autowired
    private final PhilzCartRepository repository;

    @Autowired
    private final PhilzProductRepository productRepository;
    private List<PhilzProduct> productList;

    /**
     * PhilzCart constructor
     * @param productRepository
     * @param repository
     */
    PhilzCartController(PhilzProductRepository productRepository, PhilzCartRepository repository) {
        this.productRepository = productRepository;
        this.repository = repository;
    }


    /**
     * Message Object
     */
    class Message {
        @Getter
        @Setter
        private String status;
    }


    private HashMap<Long, List<PhilzProducts>> orders = new HashMap<>(); // String - id, PhilzOrder - order itself
    private List<PhilzProducts> productsList = new ArrayList<>();

    @GetMapping("/products")
    Iterable<PhilzProducts> getAllOrders() {
        return this.productRepository.findAll();
    }


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


    //Get info about specific active order

    /**
     * Get the current user's cart
     * @param username: Current user
     * @param response
     * @return List of PhilzProducts
     */
    @GetMapping("api/cart/{username}")
    List<PhilzProducts> getActiveOrder(@PathVariable String username, HttpServletResponse response) {
        PhilzCart active = repository.findByUsername(username);

        if (active != null) {
            return productsList;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!") ;
        }

    }

    /**
     * Add PhilzProducts to PhilzCart
     * @param username
     * @param coffee
     * @return
     */
    @PostMapping("api/cart/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    PhilzCart newOrder(@PathVariable String username, @RequestBody PhilzProducts coffee) {

        System.out.println("Creating order " + username + ": " + coffee);

        PhilzProducts product = productRepository.findAllByName(coffee.name);

        PhilzCart active = repository.findByUsername(username);
        if (active == null) {
            active = new PhilzCart();
            productsList = new ArrayList<>();
        }


        double total = product.getPrice() * 0.0725;
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total + scale) / scale;
        double running_total = product.getPrice() + active.getTotal() + rounded;
        productsList.add(product);
        active.addProduct(productsList);
        active.setUsername(username);
        active.setStatus(Status.IN_PROGRESS);
        active.setTotal(running_total);
        PhilzCart new_order = repository.save(active);


        orders.put(new_order.getId(), productsList);

        return new_order;
    }


//    // delete all orders

    /**
     * Delete orders
     * @return
     */
    @DeleteMapping("api/cart/{username}/delete")
    Message deleteAll() {
        this.repository.deleteAllInBatch();
        this.orders.clear();
        Message msg = new Message();
        msg.setStatus("All Orders Deleted!");
        return msg;
    }
}
