package com.example.philzproducts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "rest")
public class PhilzProductController {
    private List<PhilzProduct> productList; 

    /**
     * Constructor: Adds all the types of products
     */
    PhilzProductController(){
        this.productList.add(new PhilzProduct(0, BlendType.DARK.toString(),"Jacob's Wonderbar", 18.50 )); 
        this.productList.add(new PhilzProduct(1, BlendType.DARK.toString(),"Ecstatic", 18.50 )); 
        this.productList.add(new PhilzProduct(2, BlendType.DARK.toString(),"Jacob's Wonderbar 4lb Whole Bean", 74.00 ));
        this.productList.add(new PhilzProduct(3, BlendType.DARK.toString(),"Aromatic Arabic", 18.50 ));  
        this.productList.add(new PhilzProduct(4, BlendType.DARK.toString(),"Ether", 18.50 )); 
        this.productList.add(new PhilzProduct(5, BlendType.DARK.toString(),"Tantalizing Turkish", 18.50 )); 
        this.productList.add(new PhilzProduct(6, BlendType.MEDIUM.toString(),"Gratitude", 18.50 )); 
        this.productList.add(new PhilzProduct(7, BlendType.MEDIUM.toString(),"Greater Alarm", 18.50 )); 
        this.productList.add(new PhilzProduct(8,BlendType.MEDIUM.toString(),"Julie's Ultimate", 18.50 )); 
        this.productList.add(new PhilzProduct(9, BlendType.MEDIUM.toString(),"Philharmonic", 18.50 )); 
        this.productList.add(new PhilzProduct(10, BlendType.MEDIUM.toString(),"Tesora", 18.50 )); 
        this.productList.add(new PhilzProduct(11, BlendType.MEDIUM.toString(),"Tesora 4lb Whole Bean", 74.00 ));
        this.productList.add(new PhilzProduct(12, BlendType.MEDIUM.toString(),"Philtered Soul 4lb Whole Bean", 74.00 ));  
        this.productList.add(new PhilzProduct(13, BlendType.MEDIUM.toString(),"Philtered Soul", 18.50 ));
        this.productList.add(new PhilzProduct(14, BlendType.MEDIUM.toString(),"Silken Splendor", 18.50 )); 
        this.productList.add(new PhilzProduct(15, BlendType.LIGHT.toString(),"Ambrosia's Coffee of God", 18.50 ));
        this.productList.add(new PhilzProduct(16, BlendType.LIGHT.toString(),"Dancing Water", 18.50 ));
        this.productList.add(new PhilzProduct(17, BlendType.LIGHT.toString(),"New Manhattan", 18.50 ));
        this.productList.add(new PhilzProduct(18, BlendType.DECAF.toString(),"Decaf Columbia Darker Roast", 18.50 ));
        this.productList.add(new PhilzProduct(19, BlendType.DECAF.toString(),"Decaf Ethiopia Lighter Roast", 18.50 ));
        this.productList.add(new PhilzProduct(20, BlendType.DECAF.toString(),"Decaf House Medium Blend", 18.50 ));
        this.productList.add(new PhilzProduct(21, BlendType.SINGLEORIGIN.toString(),"HazelNut", 18.50 ));
    }

    /**
     * Gets all the products
     * @return a list as an iterable of all the products
     */
    @GetMapping(value="/products")
    Iterable<PhilzProduct> getAllProducts() {
        return this.productList; 
    }

    /**
     * Returns details about a single product
     * @param productID the product id from 0-21
     * @return Response Entity with product as a json string and http status
     */
    @GetMapping(value="/products/{productid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getProduct(@PathVariable String productID){
        System.out.println("getProduct: " + productID); 
        JSONObject jsonObject = new JSONObject(); 
        try{
            jsonObject.put("product",  this.productList.get(Integer.parseInt(productID))); 
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

}
