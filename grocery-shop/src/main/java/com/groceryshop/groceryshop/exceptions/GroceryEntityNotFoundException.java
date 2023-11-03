package com.groceryshop.groceryshop.exceptions;

public class GroceryEntityNotFoundException extends RuntimeException {

    public GroceryEntityNotFoundException(String message) {
        super(message);
    }
}
