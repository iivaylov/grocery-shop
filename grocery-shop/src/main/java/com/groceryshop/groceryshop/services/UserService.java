package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.controllers.requests.RegisterUserRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface UserService {

    UserDTO getUserById(HttpHeaders headers, int userId);

    List<ProductDTO> getUserShoppingList(HttpHeaders headers, int userId);

    List<ProductDTO> getUserShoppingList(HttpHeaders headers);

    void addProductToShoppingList(HttpHeaders headers, int userId, int productId);

    void removeProductFromShoppingList(HttpHeaders headers, int userId, int productId);

    UserDTO getUserByUsername(String username);

    void register(RegisterUserRequest registerUserRequest);
}
