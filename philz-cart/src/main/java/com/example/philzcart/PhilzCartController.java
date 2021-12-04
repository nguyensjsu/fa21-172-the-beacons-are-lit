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



    /**
     * Message Object
     */
    class Message {
        @Getter
        @Setter
        private String status;
    }


    private HashMap<Long, PhilzProducts> orders = new HashMap<>(); // String - id, PhilzOrder - order itself
    private List<PhilzProducts> productsList = new ArrayList<>();

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
    List<PhilzProducts> getActiveOrder(@PathVariable String userid, HttpServletResponse response) {
        PhilzCart active = repository.findByUserId(userid);
        for (PhilzProducts product : productsList){
            System.out.println(product);
        }
        for (PhilzProducts products : orders.values()){
            System.out.println(products);
        }
        if (active != null) {
            return active.getOrder();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!") ;
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
        active.setUserId(userid);

        active.setStatus(Status.IN_PROGRESS);
        active.setTotal(running_total);
        PhilzCart new_order = repository.save(active);
        orders.put(new_order.getId(), product);


        return new_order;
    }

    // Clear a specific order
//    @DeleteMapping("/order/{userid}/delete")
//    Message deleteActiveOrder(@PathVariable String userid) {
//        PhilzCart active = this.orders.get(userid);
//        if (active != null) {
//            this.orders.remove(userid);
//            Message msg = new Message();
//            msg.setStatus("Active Order Cleared!");
//            return msg;
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
//        }
//    }


//    // delete all orders
    @DeleteMapping("/cart/{userid}/delete")
    Message deleteAll() {
        this.repository.deleteAllInBatch();
        this.orders.clear();
        Message msg = new Message();
        msg.setStatus("All Orders Deleted!");
        return msg;
    }
}
