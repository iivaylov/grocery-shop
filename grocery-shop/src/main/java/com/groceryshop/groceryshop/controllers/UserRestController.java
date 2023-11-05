package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    public static final String ADD_PRODUCT_MSG = "You have successfully added the product to your shopping list.";
    public static final String REMOVE_PRODUCT_MSG = "You have successfully removed this item from your shopping list.";

    public static final String GET_METHOD_CALLED_WITH_HEADERS =
            "UserRestController.get() called with headers: {}";
    public static final String GET_METHOD_CALLED_WITH_USER_ID =
            "UserRestController.get() called with userId: {}";
    public static final String GET_SHOPPING_LIST_CALLED_WITH_HEADERS =
            "UserRestController.getShoppingList() called with headers: {}";
    public static final String GET_SHOPPING_LIST_CALLED_WITH_USER_ID =
            "UserRestController.getShoppingList() called with userId: {}";
    public static final String ADD_TO_SHOPPING_LIST_CALLED_WITH_HEADERS =
            "UserRestController.addToShoppingList() called with headers: {}";
    public static final String ADD_TO_SHOPPING_LIST_CALLED_WITH_USER_ID =
            "UserRestController.addToShoppingList() called with userId: {}";
    public static final String ADD_TO_SHOPPING_LIST_CALLED_WITH_PRODUCT_ID =
            "UserRestController.addToShoppingList() called with productId: {}";
    public static final String REMOVE_FROM_SHOPPING_LIST_CALLED_WITH_HEADERS =
            "UserRestController.removeFromShoppingList() called with headers: {}";
    public static final String REMOVE_FROM_SHOPPING_LIST_CALLED_WITH_USER_ID =
            "UserRestController.removeFromShoppingList() called with userId: {}";
    public static final String REMOVE_FROM_SHOPPING_LIST_CALLED_WITH_PRODUCT_ID =
            "UserRestController.removeFromShoppingList() called with productId: {}";

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> get(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId) {

        log.info(GET_METHOD_CALLED_WITH_HEADERS, headers);
        log.info(GET_METHOD_CALLED_WITH_USER_ID, userId);

        UserDTO userToReturn = userService.getUserById(headers, userId);
        return new ResponseEntity<>(userToReturn, HttpStatus.OK);
    }

    @GetMapping("/{userId}/shopping-list")
    public ResponseEntity<List<ProductDTO>> getShoppingList(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId) {

        log.info(GET_SHOPPING_LIST_CALLED_WITH_HEADERS, headers);
        log.info(GET_SHOPPING_LIST_CALLED_WITH_USER_ID, userId);

        List<ProductDTO> userShoppingList = userService.getUserShoppingList(headers, userId);
        return new ResponseEntity<>(userShoppingList, HttpStatus.OK);
    }

    @PutMapping("/{userId}/shopping-list/{productId}")
    public ResponseEntity<String> addToShoppingList(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId,
            @PathVariable int productId) {

        log.info(ADD_TO_SHOPPING_LIST_CALLED_WITH_HEADERS, headers);
        log.info(ADD_TO_SHOPPING_LIST_CALLED_WITH_USER_ID, userId);
        log.info(ADD_TO_SHOPPING_LIST_CALLED_WITH_PRODUCT_ID, productId);

        userService.addProductToShoppingList(headers, userId, productId);
        return new ResponseEntity<>(ADD_PRODUCT_MSG, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/shopping-list/{productId}")
    public ResponseEntity<String> removeFromShoppingList(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId,
            @PathVariable int productId) {

        log.info(REMOVE_FROM_SHOPPING_LIST_CALLED_WITH_HEADERS, headers);
        log.info(REMOVE_FROM_SHOPPING_LIST_CALLED_WITH_USER_ID, userId);
        log.info(REMOVE_FROM_SHOPPING_LIST_CALLED_WITH_PRODUCT_ID, productId);

        userService.removeProductFromShoppingList(headers, userId, productId);
        return new ResponseEntity<>(REMOVE_PRODUCT_MSG, HttpStatus.OK);
    }
}
