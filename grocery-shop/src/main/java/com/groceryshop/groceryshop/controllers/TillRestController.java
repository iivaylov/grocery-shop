package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/till")
public class TillRestController {
    private final UserService userService;
    private final TillService tillService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping("/calculate-bill")
    public ResponseEntity<String> calculateBill(@RequestHeader HttpHeaders headers) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        List<ProductDTO> userShoppingList = userService.getUserShoppingList(currentUser.id());
        String billResult = tillService.calculateUserBill(userShoppingList);
        return new ResponseEntity<>(billResult, HttpStatus.OK);
    }
}
