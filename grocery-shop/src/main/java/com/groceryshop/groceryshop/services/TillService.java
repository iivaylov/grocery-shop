package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.dtos.ProductDTO;

import java.util.List;

public interface TillService {

    String calculateUserBill(List<ProductDTO> productsDTOs);
}
