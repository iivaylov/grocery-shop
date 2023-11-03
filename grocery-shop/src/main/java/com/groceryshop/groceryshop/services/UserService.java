package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserById(int userId);

    UserDTO getUserByUsername(String username);

    List<ProductDTO> getUserShoppingList(int userId);

    void addProductToShoppingList(int userId, int productId);

    void removeProductFromShoppingList(int userId, int productId);

    boolean authenticate(String username, String password);
}
