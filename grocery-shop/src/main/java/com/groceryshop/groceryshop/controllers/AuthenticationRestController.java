package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.controllers.requests.RegisterUserRequest;
import com.groceryshop.groceryshop.services.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestController {

    public static final String REGISTERED_MSG = "You have successfully registered.";
    public static final String REGISTER_CALLED_WITH_REGISTER_REQUEST =
            "AuthenticationRestController.register() called with request: {}";
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserRequest request) {

        log.info(REGISTER_CALLED_WITH_REGISTER_REQUEST, request);

        userService.register(request);
        return new ResponseEntity<>(REGISTERED_MSG, HttpStatus.CREATED);
    }
}
