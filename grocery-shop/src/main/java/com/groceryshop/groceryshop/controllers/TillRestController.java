package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/till")
public class TillRestController {

    //Price -> String // 1aws and 23clouds , HttpStatus.OK

    private final TillService tillService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TillRestController(TillService tillService, AuthenticationHelper authenticationHelper) {
        this.tillService = tillService;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/calculate-bill")
    public ResponseEntity<String> calculateBill(@RequestHeader HttpHeaders headers) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        String billResult = tillService.calculateUserBill(currentUser);
        return new ResponseEntity<>(billResult, HttpStatus.OK);
    }
}
