package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO getUserById(int userId) {
        return null;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return null;
    }

    @Override
    public List<ProductDTO> getUserShoppingList(int userId) {
        return null;
    }

    @Override
    public void addProductToShoppingList(int userId, int productId) {

    }

    @Override
    public void removeProductFromShoppingList(int userId, int productId) {

    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }
}
