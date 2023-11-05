package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.repositories.UserDAO;
import com.groceryshop.groceryshop.services.impls.UserServiceImpl;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import com.groceryshop.groceryshop.services.mappers.UserDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceImplTests {

    @Mock
    UserDAO mockUserDAO;

    @Mock
    ProductDAO mockProductDAO;

    @Mock
    UserDTOMapper mockUserDTOMapper;

    @Mock
    ProductDTOMapper mockProductDTOMapper;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        int userId = 1;
        Mockito.when(mockUserDAO.selectUserById(userId))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(GroceryEntityNotFoundException.class, () ->
                service.getUserById(userId));
    }

}
