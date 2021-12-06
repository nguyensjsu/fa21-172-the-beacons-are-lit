package com.example.philzproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "rest")
public class PhilzProductController {

    @Autowired
    private final PhilzProductRepository productRepository;

    private HashMap<Long, List<PhilzProducts>> orders = new HashMap<>(); // String - id, PhilzOrder - order itself
    private List<PhilzProducts> productsList = new ArrayList<>();

    /**
     * PhilzCart constructor
     * @param productRepository
     * @param repository
     */
    PhilzProductController(PhilzProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    /**
     * Message Object
     */
    class Message {
        @Getter
        @Setter
        private String status;
    }

    /**
     * Gets all existing product types
     * @return
     */
    @GetMapping("/products")
    Iterable<PhilzProducts> getAllProducts() {
        return this.productRepository.findAll();
    }

      /**
     * Returns details about a single product
     * @param productid the product id from 0-21
     * @return Response Entity with product as a json string and http status
     */
    @GetMapping(value="/products/{productid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getProduct(@PathVariable String productid, Model model){
        System.out.println("getProduct: " + productid);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("product",  productRepository.findByProductID(productid));

            //return "payments";
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        }catch(JSONException e){
            try {
				jsonObject.put("exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Delete orders
     * @return
     */
    @DeleteMapping("api/cart/{username}/delete")
    Message deleteAll() {
        this.orders.clear();
        Message msg = new Message();
        msg.setStatus("All Orders Deleted!");
        return msg;
    }
}
