package com.groceryshop.groceryshop.exceptions;

public class GroceryDuplicateEntity extends RuntimeException {

    public GroceryDuplicateEntity(String message) {
        super(message);
    }
}
