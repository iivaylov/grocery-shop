package com.groceryshop.groceryshop.exceptions;

public class GroceryAuthenticationError extends RuntimeException {

    public GroceryAuthenticationError(String message) {
        super(message);
    }
}
