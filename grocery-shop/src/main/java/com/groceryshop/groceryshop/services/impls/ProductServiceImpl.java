package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.services.ProductService;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_ID_NOT_FOUND = "Product with id [%s] not found.";
    public static final String PRODUCT_NAME_NOT_FOUND = "Product named [%s] not found.";
    public static final String PRODUCT_ALREADY_EXISTS = "Product named [%s] already exists.";
    private final ProductDAO productDAO;
    private final ProductDTOMapper productDTOMapper;

    @Autowired
    public ProductServiceImpl(
            ProductDAO productDAO,
            ProductDTOMapper productDTOMapper) {
        this.productDAO = productDAO;
        this.productDTOMapper = productDTOMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productDAO.selectAllProducts()
                .stream()
                .map(productDTOMapper)
                .toList();
    }

    @Override
    public ProductDTO getProductById(int productId) {
        return productDAO.selectProductById(productId)
                .map(productDTOMapper)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        PRODUCT_ID_NOT_FOUND.formatted(productId)
                ));
    }

    @Override
    public ProductDTO createProduct(ProductRequest productRequest, UserDTO userDTO) {
        Optional<ProductEntity> existingProduct = productDAO.selectProductByName(productRequest.getName());

        //TODO: Check user permission

        if (existingProduct.isPresent()) {
            throw new GroceryDuplicateEntityException(PRODUCT_ALREADY_EXISTS.formatted(productRequest.getName()));
        }

        ProductEntity productEntity = ProductEntity.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();

        productDAO.insertProduct(productEntity);

        return productDTOMapper.apply(productEntity);
    }

    @Override
    public ProductDTO updateProduct(ProductRequest productRequest, UserDTO userDTO) {
        Optional<ProductEntity> existingProduct = productDAO.selectProductByName(productRequest.getName());

        //TODO: Check user permission

        ProductEntity updatedProductEntity = existingProduct.map(product -> {
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());

            productDAO.updateProduct(product);
            return product;
        }).orElseThrow(() -> new GroceryEntityNotFoundException(
                PRODUCT_NAME_NOT_FOUND.formatted(productRequest.getName())
        ));

        return productDTOMapper.apply(updatedProductEntity);
    }

    @Override
    public void deleteProduct(int productId, UserDTO userDTO) {
        ProductEntity productEntity = productDAO.selectProductById(productId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        PRODUCT_ID_NOT_FOUND.formatted(productId)
                ));

        //TODO: Check user permission

        productDAO.deleteProduct(productEntity);
    }
}
