package com.groceryshop.groceryshop.services;

import org.springframework.http.HttpHeaders;

public interface TillService {

    String calculateUserBill(HttpHeaders headers);
}
