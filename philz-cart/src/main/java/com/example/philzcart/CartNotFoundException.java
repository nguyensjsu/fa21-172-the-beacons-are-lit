package com.example.philzcart;

public class CartNotFoundException extends RuntimeException {

    CartNotFoundException(String id) {
        super("Could not find order for " + id);
    }
}
