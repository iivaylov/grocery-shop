package com.groceryshop.groceryshop.exceptions;

public class GroceryDuplicateEntityException extends RuntimeException {

    public GroceryDuplicateEntityException(String message) {
        super(message);
    }
}
