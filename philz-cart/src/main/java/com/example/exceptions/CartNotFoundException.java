package com.example.exceptions;

public class CartNotFoundException extends RuntimeException {

    CartNotFoundException(String id) {
        super("Could not find order for " + id);
    }
}
