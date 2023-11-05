package com.groceryshop.groceryshop;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.models.UserEntity;

import java.util.List;

public class Helpers {

    public static ProductEntity createMockProduct() {
        return ProductEntity.builder()
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

    public static ProductRequest createMockProductRequest() {
        return ProductRequest.builder()
                .name("MockProduct")
                .price(10)
                .build();
    }

    public static UserEntity createMockUser() {
        return UserEntity.builder()
                .id(1)
                .username("MockUser")
                .password("MockPassword")
                .firstName("MockFirstName")
                .lastName("MockLastName")
                .shoppingList(List.of(createMockProduct()))
                .build();
    }

    public static UserDTO createMockUserDTO() {
        return UserDTO.builder()
                .id(1)
                .username("MockUser")
                .firstname("MockFirstName")
                .lastName("MockLastName")
                .build();
    }
}
