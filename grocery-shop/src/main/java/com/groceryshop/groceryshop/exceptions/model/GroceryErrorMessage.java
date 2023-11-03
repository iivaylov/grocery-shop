package com.groceryshop.groceryshop.exceptions.model;

import org.springframework.http.HttpStatus;

public record GroceryErrorMessage(
        String message,

        HttpStatus httpStatus
) {
}
