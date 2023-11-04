package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.Product;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.services.impls.ProductServiceImpl;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.groceryshop.groceryshop.Helpers.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTests {

    @Mock
    ProductDAO mockProductDAO;

    @Mock
    ProductDTOMapper mockProductDTOMapper;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    void get_Should_ReturnProduct_When_MatchByIdExist() {
        // Arrange
        Product mockProduct = createMockProduct();
        ProductDTO mockProductDTO = createMockProductDTO();

        Mockito.when(mockProductDAO.selectProductById(Mockito.anyInt()))
                .thenReturn(Optional.of(mockProduct));

        Mockito.when(mockProductDTOMapper.apply(mockProduct))
                .thenReturn(mockProductDTO);

        // Act
        ProductDTO result = service.getProductById(mockProduct.getId());

        // Assert
        Assertions.assertEquals(mockProductDTO, result);
    }

    @Test
    void getAllProducts_Should_ReturnListOfProductDTOs() {
        // Arrange
        Product product1 = new Product("Apple", 10);
        Product product2 = new Product("Banana", 75);
        List<Product> productList = List.of(product1, product2);
        List<ProductDTO> productDTOList = productList.stream()
                .map(product -> new ProductDTO(product.getName(), product.getPrice()))
                .toList();

        Mockito.when(mockProductDAO.selectAllProducts()).thenReturn(productList);
        Mockito.when(mockProductDTOMapper.apply(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            return new ProductDTO(product.getName(), product.getPrice());
        });

        // Act
        List<ProductDTO> actualProductDTOList = service.getAllProducts();

        // Assert
        Assertions.assertEquals(productDTOList.size(), actualProductDTOList.size());
        for (int i = 0; i < productDTOList.size(); i++) {
            ProductDTO expectedDTO = productDTOList.get(i);
            ProductDTO actualDTO = actualProductDTOList.get(i);
            Assertions.assertEquals(expectedDTO.name(), actualDTO.name());
            Assertions.assertEquals(expectedDTO.price(), actualDTO.price());
        }

        // Optionally, you can also verify the interaction:
        Mockito.verify(mockProductDAO).selectAllProducts();
        Mockito.verify(mockProductDTOMapper, Mockito.times(productList.size())).apply(any(Product.class));
    }

    @Test
    void createProduct_Should_CreateNewProduct_When_ProductNameNotTaken() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        UserDTO userDTO = createMockUserDTO();
        ProductDTO mockProductDTO = createMockProductDTO();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName()))
                .thenReturn(Optional.empty());

        Mockito.when(mockProductDTOMapper.apply(any(Product.class)))
                .thenReturn(mockProductDTO);

        //Act
        ProductDTO result = service.createProduct(productRequest, userDTO);

        //Assert
        Mockito.verify(mockProductDAO).insertProduct(any(Product.class));
        Assertions.assertEquals(mockProductDTO, result);
    }

    @Test
    void createProduct_Should_ThrowException_When_ProductNameTaken() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        UserDTO userDTO = createMockUserDTO();
        Product existingProduct = createMockProduct();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName())).thenReturn(Optional.of(existingProduct));

        //Act & Assert
        Assertions.assertThrows(
                GroceryDuplicateEntityException.class,
                () -> service.createProduct(productRequest, userDTO));
    }

    @Test
    void updateProduct_Should_UpdateProduct_When_ProductExists() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        UserDTO userDTO = createMockUserDTO();
        Product existingProduct = createMockProduct();
        ProductDTO expectedProductDTO = createMockProductDTO();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName())).thenReturn(Optional.of(existingProduct));
        Mockito.when(mockProductDTOMapper.apply(any(Product.class))).thenReturn(expectedProductDTO);

        // Act
        ProductDTO actualProductDTO = service.updateProduct(productRequest, userDTO);

        // Assert
        Mockito.verify(mockProductDAO).updateProduct(any(Product.class));
        Assertions.assertEquals(expectedProductDTO, actualProductDTO);
    }

    @Test
    void updateProduct_Should_ThrowException_When_ProductDoesNotExist() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        UserDTO userDTO = createMockUserDTO();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                GroceryEntityNotFoundException.class,
                () -> service.updateProduct(productRequest, userDTO));
    }

    @Test
    void deleteProduct_Should_DeleteProduct_When_ProductExists() {
        // Arrange
        int productId = 1;
        Product mockProduct = createMockProduct();
        UserDTO userDTO = createMockUserDTO();
        Mockito.when(mockProductDAO.selectProductById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        service.deleteProduct(productId, userDTO);

        // Assert
        Mockito.verify(mockProductDAO).deleteProduct(mockProduct);
    }

    @Test
    void deleteProduct_Should_ThrowException_When_ProductDoesNotExist() {
        // Arrange
        int productId = 1;
        UserDTO userDTO = createMockUserDTO();
        Mockito.when(mockProductDAO.selectProductById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                GroceryEntityNotFoundException.class,
                () -> service.deleteProduct(productId, userDTO));
    }

}
