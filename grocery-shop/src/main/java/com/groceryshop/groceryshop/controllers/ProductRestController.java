package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.exceptions.GroceryEntityNotFound;
import com.groceryshop.groceryshop.services.ProductService;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    public static final String DELETE_PRODUCT_MSG = "The product has been successfully deleted.";
    public static final String PRODUCT_ERROR_MSG = "Product with the provided name does not match the existing one.";
    private final ProductService productService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ProductRestController(ProductService productService, AuthenticationHelper authenticationHelper) {
        this.productService = productService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable int productId) {
        ProductDTO product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody ProductRequest productRequest) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        ProductDTO createdProduct = productService.createProduct(productRequest, currentUser);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestHeader HttpHeaders headers,
            @PathVariable int productId,
            @Valid @RequestBody ProductRequest productRequest) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        ProductDTO productToUpdate = productService.getProductById(productId);
        ensureProductNamesMatch(productRequest, productToUpdate);
        ProductDTO updatedProduct = productService.updateProduct(productRequest, currentUser);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader HttpHeaders headers,
            @PathVariable int productId) {
        UserDTO currentUser = authenticationHelper.tryGetUser(headers);
        productService.deleteProduct(productId, currentUser);
        return new ResponseEntity<>(DELETE_PRODUCT_MSG, HttpStatus.OK);
    }

    private static void ensureProductNamesMatch(ProductRequest productRequest, ProductDTO productToUpdate) {
        if (!productToUpdate.name().equals(productRequest.getName())) {
            throw new GroceryEntityNotFound(PRODUCT_ERROR_MSG);
        }
    }

}
