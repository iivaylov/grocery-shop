package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.controllers.requests.RegisterUserRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryAuthenticationException;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.Product;
import com.groceryshop.groceryshop.models.User;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.repositories.UserDAO;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import com.groceryshop.groceryshop.services.mappers.UserDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    @Autowired
    public UserServiceImpl(
            UserDAO userDAO,
            ProductDAO productDAO,
            ProductDTOMapper productDTOMapper, UserDTOMapper userDTOMapper) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
        this.productDTOMapper = productDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

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
        User user = getUserFromRepository(userId);
        return user.getShoppingList()
                .stream()
                .map(productDTOMapper)
                .toList();
    }

    @Override
    public void addProductToShoppingList(int userId, int productId) {
        User user = getUserFromRepository(userId);
        Product product = productDAO.selectProductById(productId).orElseThrow();
        user.getShoppingList().add(product);
        userDAO.updateUser(user);
    }

    @Override
    public void removeProductFromShoppingList(int userId, int productId) {
        User user = getUserFromRepository(userId);

        if (user.getShoppingList().stream().noneMatch(product -> product.getId() == productId)) {
            throw new GroceryEntityNotFoundException(PRODUCT_ID_NOT_FOUND.formatted(productId));
        }

        user.getShoppingList().removeIf(product -> product.getId() == productId);
        userDAO.updateUser(user);
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userDAO.selectUserByUsername(username)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        USER_USERNAME_NOT_FOUND.formatted(username)
                ));

        if (!user.getPassword().equals(password)) {
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

        User newUser = buildUserFromRequest(registerUserRequest);
        userDAO.insertUser(newUser);
    }

    private User getUserFromRepository(int userId) {
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

    private User buildUserFromRequest(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .firstName(registerUserRequest.getFirstname())
                .lastName(registerUserRequest.getLastname())
                .build();
    }
}
