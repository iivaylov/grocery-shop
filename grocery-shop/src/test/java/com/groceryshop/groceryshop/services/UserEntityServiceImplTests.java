package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.repositories.UserDAO;
import com.groceryshop.groceryshop.services.impls.UserServiceImpl;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import com.groceryshop.groceryshop.services.mappers.UserDTOMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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

}
