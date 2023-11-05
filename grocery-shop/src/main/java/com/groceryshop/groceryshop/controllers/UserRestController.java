package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryAuthorizationException;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
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
    public static final String ERROR_MESSAGE = "You are not authorized to browse user information.";
    public static final String ADD_PRODUCT_MSG = "You have successfully added the product to your shopping list.";
    public static final String REMOVE_PRODUCT_MSG = "You have successfully removed this item from your shopping list.";

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> get(@RequestHeader HttpHeaders headers, @PathVariable int userId) {

        log.info("UserRestController get()");
        log.info("UserRestController get() userId: " + userId);
        log.info("UserRestController get() headers: " + headers);

        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        checkAccessPermissions(userId, currentUser);
        UserDTO userToReturn = userService.getUserById(userId);
        return new ResponseEntity<>(userToReturn, HttpStatus.OK);
    }

    @GetMapping("/{userId}/shopping-list")
    public ResponseEntity<List<ProductDTO>> getShoppingList(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        checkAccessPermissions(userId, currentUser);
        List<ProductDTO> userShoppingList = userService.getUserShoppingList(userId);
        return new ResponseEntity<>(userShoppingList, HttpStatus.OK);
    }

    @PutMapping("/{userId}/shopping-list/{productId}")
    public ResponseEntity<String> addToShoppingList(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId,
            @PathVariable int productId) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        checkAccessPermissions(userId, currentUser);
        userService.addProductToShoppingList(userId, productId);
        return new ResponseEntity<>(ADD_PRODUCT_MSG, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/shopping-list/{productId}")
    public ResponseEntity<String> removeFromShoppingList(
            @RequestHeader HttpHeaders headers,
            @PathVariable int userId,
            @PathVariable int productId) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        checkAccessPermissions(userId, currentUser);
        userService.removeProductFromShoppingList(userId, productId);
        return new ResponseEntity<>(REMOVE_PRODUCT_MSG, HttpStatus.OK);
    }

    private static void checkAccessPermissions(int targetUserId, UserDTO executingUser) {
        if (executingUser.id() != targetUserId) {
            throw new GroceryAuthorizationException(ERROR_MESSAGE);
        }
    }
}
