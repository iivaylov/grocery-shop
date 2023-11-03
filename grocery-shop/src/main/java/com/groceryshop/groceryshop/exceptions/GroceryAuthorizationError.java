package com.groceryshop.groceryshop.exceptions;

public class GroceryAuthorizationError extends RuntimeException {

    public GroceryAuthorizationError(String message) {
        super(message);
    }
}
