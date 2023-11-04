package com.groceryshop.groceryshop;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.models.Product;

public class Helpers {

    public static Product createMockProduct() {
        return Product.builder()
                .id(1)
                .name("MockProduct")
                .price(10)
                .build();
    }

    public static ProductDTO createMockProductDTO() {
        return ProductDTO.builder()
                .name("MockProduct")
                .price(10)
                .build();
    }

    public static UserDTO createMockUserDTO() {
        return UserDTO.builder()
                .id(1)
                .username("MockUser")
                .lastName("MockLastName")
                .build();
    }

    public static ProductRequest createMockProductRequest() {
        return ProductRequest.builder()
                .name("MockProduct")
                .price(10)
                .build();
    }
}
