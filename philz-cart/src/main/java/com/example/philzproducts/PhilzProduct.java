package com.example.philzproducts;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PhilzProduct {
    private int productID; 
    private String blendType; 
    private String blend;
    private double price; 
}
