package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/till")
public class TillRestController {
    private final UserService userService;
    private final TillService tillService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TillRestController(
            UserService userService,
            TillService tillService,
            AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.tillService = tillService;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/calculate-bill")
    public ResponseEntity<String> calculateBill(@RequestHeader HttpHeaders headers) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        List<ProductDTO> userShoppingList = userService.getUserShoppingList(currentUser.id());
        String billResult = tillService.calculateUserBill(userShoppingList);
        return new ResponseEntity<>(billResult, HttpStatus.OK);
    }
}
