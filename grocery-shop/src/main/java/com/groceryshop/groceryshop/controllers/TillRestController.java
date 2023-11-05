package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.services.TillService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/till")
public class TillRestController {
    public static final String CALCULATE_BILL_CALLED_WITH_HEADERS =
            "TillRestController.calculateBill() called with headers: {}";

    private final TillService tillService;

    @PostMapping("/calculate-bill")
    public ResponseEntity<String> calculateBill(@RequestHeader HttpHeaders headers) {

        log.info(CALCULATE_BILL_CALLED_WITH_HEADERS, headers);

        String billResult = tillService.calculateUserBill(headers);
        return new ResponseEntity<>(billResult, HttpStatus.OK);
    }
}
