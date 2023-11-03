package com.groceryshop.groceryshop.exceptions.handler;

import com.groceryshop.groceryshop.exceptions.GroceryAuthenticationException;
import com.groceryshop.groceryshop.exceptions.GroceryAuthorizationException;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.exceptions.model.GroceryErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {GroceryAuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(
            GroceryAuthenticationException groceryAuthenticationException) {
        GroceryErrorMessage errorMessage = new GroceryErrorMessage(
                groceryAuthenticationException.getMessage(),
                HttpStatus.UNAUTHORIZED
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {GroceryAuthorizationException.class})
    public ResponseEntity<Object> handleAuthorizationException(
            GroceryAuthorizationException groceryAuthorizationException) {
        GroceryErrorMessage errorMessage = new GroceryErrorMessage(
                groceryAuthorizationException.getMessage(),
                HttpStatus.FORBIDDEN
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {GroceryDuplicateEntityException.class})
    public ResponseEntity<Object> handleDuplicateEntityException(
            GroceryDuplicateEntityException groceryDuplicateEntityException) {
        GroceryErrorMessage errorMessage = new GroceryErrorMessage(
                groceryDuplicateEntityException.getMessage(),
                HttpStatus.CONFLICT
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {GroceryEntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            GroceryEntityNotFoundException groceryEntityNotFoundException) {
        GroceryErrorMessage errorMessage = new GroceryErrorMessage(
                groceryEntityNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
