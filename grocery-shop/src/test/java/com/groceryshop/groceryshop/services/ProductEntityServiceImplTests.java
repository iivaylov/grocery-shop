package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.ProductEntity;
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
class ProductEntityServiceImplTests {

    @Mock
    ProductDAO mockProductDAO;

    @Mock
    ProductDTOMapper mockProductDTOMapper;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    void get_Should_ReturnProduct_When_MatchByIdExist() {
        // Arrange
        ProductEntity mockProductEntity = createMockProduct();
        ProductDTO mockProductDTO = createMockProductDTO();

        Mockito.when(mockProductDAO.selectProductById(Mockito.anyInt()))
                .thenReturn(Optional.of(mockProductEntity));

        Mockito.when(mockProductDTOMapper.apply(mockProductEntity))
                .thenReturn(mockProductDTO);

        // Act
        ProductDTO result = service.getProductById(mockProductEntity.getId());

        // Assert
        Assertions.assertEquals(mockProductDTO, result);
    }

    @Test
    void getAllProducts_Should_ReturnListOfProductDTOs() {
        // Arrange
        ProductEntity productEntity1 = new ProductEntity(1, "Apple", 10, null);
        ProductEntity productEntity2 = new ProductEntity(2, "Banana", 75, null);
        List<ProductEntity> productEntityList = List.of(productEntity1, productEntity2);
        List<ProductDTO> productDTOList = productEntityList.stream()
                .map(product -> new ProductDTO(product.getName(), product.getPrice()))
                .toList();

        Mockito.when(mockProductDAO.selectAllProducts()).thenReturn(productEntityList);
        Mockito.when(mockProductDTOMapper.apply(any(ProductEntity.class))).thenAnswer(invocation -> {
            ProductEntity productEntity = invocation.getArgument(0);
            return new ProductDTO(productEntity.getName(), productEntity.getPrice());
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

        Mockito.verify(mockProductDTOMapper, Mockito.times(productEntityList.size()))
                .apply(any(ProductEntity.class));
    }

    @Test
    void createProduct_Should_CreateNewProduct_When_ProductNameNotTaken() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        UserDTO userDTO = createMockUserDTO();
        ProductDTO mockProductDTO = createMockProductDTO();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName()))
                .thenReturn(Optional.empty());

        Mockito.when(mockProductDTOMapper.apply(any(ProductEntity.class)))
                .thenReturn(mockProductDTO);

        //Act
        ProductDTO result = service.createProduct(null, productRequest);

        //Assert
        Mockito.verify(mockProductDAO).insertProduct(any(ProductEntity.class));
        Assertions.assertEquals(mockProductDTO, result);
    }

    @Test
    void createProduct_Should_ThrowException_When_ProductNameTaken() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        ProductEntity existingProductEntity = createMockProduct();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName()))
                .thenReturn(Optional.of(existingProductEntity));

        //Act & Assert
        Assertions.assertThrows(
                GroceryDuplicateEntityException.class,
                () -> service.createProduct(null, productRequest));
    }

    @Test
    void updateProduct_Should_UpdateProduct_When_ProductExists() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();
        ProductEntity existingProductEntity = createMockProduct();
        ProductDTO expectedProductDTO = createMockProductDTO();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName()))
                .thenReturn(Optional.of(existingProductEntity));

        Mockito.when(mockProductDTOMapper.apply(any(ProductEntity.class))).thenReturn(expectedProductDTO);

        // Act
        ProductDTO actualProductDTO = service.updateProduct(null, 1, productRequest);

        // Assert
        Mockito.verify(mockProductDAO).updateProduct(any(ProductEntity.class));
        Assertions.assertEquals(expectedProductDTO, actualProductDTO);
    }

    @Test
    void updateProduct_Should_ThrowException_When_ProductDoesNotExist() {
        // Arrange
        ProductRequest productRequest = createMockProductRequest();

        Mockito.when(mockProductDAO.selectProductByName(productRequest.getName()))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                GroceryEntityNotFoundException.class,
                () -> service.updateProduct(null, 1, productRequest));
    }

    @Test
    void deleteProduct_Should_DeleteProduct_When_ProductExists() {
        // Arrange
        int productId = 1;
        ProductEntity mockProductEntity = createMockProduct();
        Mockito.when(mockProductDAO.selectProductById(productId)).thenReturn(Optional.of(mockProductEntity));

        // Act
        service.deleteProduct(null, productId);

        // Assert
        Mockito.verify(mockProductDAO).deleteProduct(mockProductEntity);
    }

    @Test
    void deleteProduct_Should_ThrowException_When_ProductDoesNotExist() {
        // Arrange
        int productId = 1;
        Mockito.when(mockProductDAO.selectProductById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                GroceryEntityNotFoundException.class,
                () -> service.deleteProduct(null, productId));
    }

}
