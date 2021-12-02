package com.example.philzapidb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServices {

	@Autowired
	ProductRepository productRepo;
	
	public List<Products>getAllProducts(){
		return productRepo.findAll();
	}
	
	
	public Products getProductsById(long productId) throws Exception {
		return productRepo.findById(productId).orElseThrow(() ->new Exception("Product is not found"));
	}
}