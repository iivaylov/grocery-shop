package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.exceptions.GroceryDuplicateEntityException;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFoundException;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.services.ProductService;
import com.groceryshop.groceryshop.services.mappers.ProductDTOMapper;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCT_ERROR_MSG = "Product with the provided name does not match the existing one.";

    public static final String PRODUCT_ID_NOT_FOUND = "Product with id [%s] not found.";
    public static final String PRODUCT_NAME_NOT_FOUND = "Product named [%s] not found.";
    public static final String PRODUCT_ALREADY_EXISTS = "Product named [%s] already exists.";
    public static final String PRODUCT_NOT_FOUND_AFTER_UPDATE = "Product not found after update.";
    private final ProductDAO productDAO;
    private final ProductDTOMapper productDTOMapper;
    private AuthenticationHelper authenticationHelper;

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
    public ProductDTO createProduct(HttpHeaders headers, ProductRequest productRequest) {
        Optional<ProductEntity> existingProduct = productDAO.selectProductByName(productRequest.getName());

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
    public ProductDTO updateProduct(HttpHeaders headers, int productId, ProductRequest productRequest) {
        ProductEntity existingProduct = productDAO.selectProductById(productId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        PRODUCT_ID_NOT_FOUND.formatted(productId)
                ));

        ensureProductNamesMatch(productRequest, existingProduct);

        existingProduct.setName(productRequest.getName());
        existingProduct.setPrice(productRequest.getPrice());

        ProductEntity updatedProductEntity = productDAO.selectProductById(productId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(PRODUCT_NOT_FOUND_AFTER_UPDATE));

        productDAO.updateProduct(updatedProductEntity);

        return productDTOMapper.apply(updatedProductEntity);
    }

    @Override
    public void deleteProduct(HttpHeaders headers, int productId) {
        ProductEntity productEntity = productDAO.selectProductById(productId)
                .orElseThrow(() -> new GroceryEntityNotFoundException(
                        PRODUCT_ID_NOT_FOUND.formatted(productId)
                ));

        productDAO.deleteProduct(productEntity);
    }

    private void ensureProductNamesMatch(ProductRequest productRequest, ProductEntity existingProduct) {
        if (!productRequest.getName().equals(existingProduct.getName())) {
            throw new GroceryEntityNotFoundException(PRODUCT_ERROR_MSG);
        }
    }
}
