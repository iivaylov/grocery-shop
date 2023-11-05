package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.controllers.requests.RegisterUserRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryAuthenticationException;
import com.groceryshop.groceryshop.exceptions.GroceryAuthorizationException;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.models.UserEntity;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.repositories.UserDAO;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import com.groceryshop.groceryshop.services.mappers.UserDTOMapper;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.groceryshop.groceryshop.utils.AuthenticationHelper.INVALID_AUTHENTICATION_ERROR;

@Data
@Service
public class UserServiceImpl implements UserService {
    public static final String USER_AUTH_ERROR_MESSAGE = "You are not authorized to browse user information.";
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
    private final AuthenticationHelper authenticationHelper;

    @Override
    public UserDTO getUserById(HttpHeaders headers, int userId) {
        authenticateUser(headers, userId);
        return userDAO.selectUserById(userId)
                .map(userDTOMapper)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        USER_ID_NOT_FOUND.formatted(userId)
                ));
    }

    @Override
    public List<ProductDTO> getUserShoppingList(HttpHeaders headers) {
        String username = authenticationHelper.tryGetUser(headers).username();
        UserDTO userDTO = getUserByUsername(username);
        return getUserShoppingList(headers, userDTO.id());
    }

    @Override
    public List<ProductDTO> getUserShoppingList(HttpHeaders headers, int userId) {
        authenticateUser(headers, userId);

        UserEntity userEntity = getUserFromRepository(userId);
        return userEntity.getShoppingList()
                .stream()
                .map(productDTOMapper)
                .toList();
    }

    @Override
    public void addProductToShoppingList(HttpHeaders headers, int userId, int productId) {
        authenticateUser(headers, userId);

        UserEntity userEntity = getUserFromRepository(userId);

        ProductEntity productEntity = productDAO.selectProductById(productId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        PRODUCT_ID_NOT_FOUND.formatted(productId)
                ));

        userEntity.getShoppingList().add(productEntity);
        userDAO.updateUser(userEntity);
    }

    @Override
    public void removeProductFromShoppingList(HttpHeaders headers, int userId, int productId) {
        authenticateUser(headers, userId);

        UserEntity userEntity = getUserFromRepository(userId);

        if (userEntity.getShoppingList().stream().noneMatch(product -> product.getId() == productId)) {
            throw new GroceryEntityNotFoundException(PRODUCT_ID_NOT_FOUND.formatted(productId));
        }

        userEntity.getShoppingList().removeIf(product -> product.getId() == productId);
        userDAO.updateUser(userEntity);
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
    public void register(RegisterUserRequest registerUserRequest) {
        validateUsernameNotExists(registerUserRequest.getUsername());

        if (!registerUserRequest.getPassword().equals(registerUserRequest.getConfirmPassword())) {
            throw new GroceryAuthenticationException(PASSWORDS_MATCH_ERROR);
        }

        UserEntity newUserEntity = buildUserFromRequest(registerUserRequest);
        userDAO.insertUser(newUserEntity);
    }

    private void authenticateUser(HttpHeaders headers, int userId) {
        AuthenticationHelper.Credentials credentials = authenticationHelper.tryGetUser(headers);
        UserDTO currentUser = authenticateUserCredentials(credentials.username(), credentials.password());
        checkAccessPermissions(userId, currentUser);
    }

    private UserDTO authenticateUserCredentials(String username, String password) {
        UserEntity userEntity;
        try {
            userEntity = userDAO.selectUserByUsername(username)
                    .orElseThrow(() -> new GroceryEntityNotFoundException(
                            USER_USERNAME_NOT_FOUND.formatted(username)
                    ));
        } catch (GroceryEntityNotFoundException e) {
            throw new GroceryAuthenticationException(INVALID_AUTHENTICATION_ERROR);
        }

        if (!userEntity.getPassword().equals(password)) {
            throw new GroceryAuthenticationException(WRONG_PASSWORD_MSG);
        }

        return getUserByUsername(username);
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

    private static void checkAccessPermissions(int targetUserId, UserDTO executingUser) {
        if (executingUser.id() != targetUserId) {
            throw new GroceryAuthorizationException(USER_AUTH_ERROR_MESSAGE);
        }
    }

    private UserEntity buildUserFromRequest(RegisterUserRequest registerUserRequest) {
        return UserEntity
                .builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .firstName(registerUserRequest.getFirstname())
                .lastName(registerUserRequest.getLastname())
                .build();
    }
}

