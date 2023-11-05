package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.controllers.requests.RegisterUserRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryAuthenticationException;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.models.UserEntity;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.repositories.UserDAO;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import com.groceryshop.groceryshop.services.mappers.UserDTOMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class UserServiceImpl implements UserService {

    public static final String USER_ID_NOT_FOUND = "User with id [%s] not found.";
    public static final String PRODUCT_ID_NOT_FOUND = "Product with id [%s] not found.";
    public static final String USER_USERNAME_NOT_FOUND = "User with username [%s] not found.";
    public static final String WRONG_PASSWORD_MSG = "Wrong password.";
    public static final String PASSWORDS_MATCH_ERROR = "Passwords do not match.";
    public static final String USERNAME_EXISTS_ERROR = "Username already exists.";
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final ProductDTOMapper productDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Override
    public UserDTO getUserById(int userId) {
        return userDAO.selectUserById(userId)
                .map(userDTOMapper)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        USER_ID_NOT_FOUND.formatted(userId)
                ));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userDAO.selectUserByUsername(username)
                .map(userDTOMapper)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        USER_USERNAME_NOT_FOUND.formatted(username)
                ));
    }

    @Override
    public List<ProductDTO> getUserShoppingList(int userId) {
        UserEntity userEntity = getUserFromRepository(userId);
        return userEntity.getShoppingList()
                .stream()
                .map(productDTOMapper)
                .toList();
    }

    @Override
    public void addProductToShoppingList(int userId, int productId) {
        UserEntity userEntity = getUserFromRepository(userId);

        ProductEntity productEntity = productDAO.selectProductById(productId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        PRODUCT_ID_NOT_FOUND.formatted(productId)
                ));

        userEntity.getShoppingList().add(productEntity);
        userDAO.updateUser(userEntity);
    }

    @Override
    public void removeProductFromShoppingList(int userId, int productId) {
        UserEntity userEntity = getUserFromRepository(userId);

        if (userEntity.getShoppingList().stream().noneMatch(product -> product.getId() == productId)) {
            throw new GroceryEntityNotFoundException(PRODUCT_ID_NOT_FOUND.formatted(productId));
        }

        userEntity.getShoppingList().removeIf(product -> product.getId() == productId);
        userDAO.updateUser(userEntity);
    }

    @Override
    public boolean authenticate(String username, String password) {
        UserEntity userEntity = userDAO.selectUserByUsername(username)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        USER_USERNAME_NOT_FOUND.formatted(username)
                ));

        if (!userEntity.getPassword().equals(password)) {
            throw new GroceryAuthenticationException(WRONG_PASSWORD_MSG);
        }

        return true;
    }

    @Override
    public void register(RegisterUserRequest registerUserRequest) {
        validateUsernameNotExists(registerUserRequest.getUsername());

        if (!registerUserRequest.getPassword().equals(registerUserRequest.getConfirmPassword())) {
            throw new GroceryAuthenticationException(PASSWORDS_MATCH_ERROR);
        }

        UserEntity newUserEntity = buildUserFromRequest(registerUserRequest);
        userDAO.insertUser(newUserEntity);
    }

    private UserEntity getUserFromRepository(int userId) {
        return userDAO.selectUserById(userId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        USER_ID_NOT_FOUND.formatted(userId)
                ));
    }

    private void validateUsernameNotExists(String username) {
        if (userDAO.selectUserByUsername(username).isPresent()) {
            throw new GroceryDuplicateEntityException(USERNAME_EXISTS_ERROR);
        }
    }

    private UserEntity buildUserFromRequest(RegisterUserRequest registerUserRequest) {
        return UserEntity.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .firstName(registerUserRequest.getFirstname())
                .lastName(registerUserRequest.getLastname())
                .build();
    }
}
