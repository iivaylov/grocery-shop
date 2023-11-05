package com.groceryshop.groceryshop.controllers;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.services.ProductService;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    public static final String DELETE_PRODUCT_MSG = "The product has been successfully deleted.";

    public static final String GET_PRODUCTS_CALLED = "ProductRestController.getProducts() called";
    public static final String GET_PRODUCT_WITH_CALLED_WITH_PRODUCT_ID =
            "ProductRestController.getProduct() called with productId: {}";
    public static final String CREATE_PRODUCT_CALLED_WITH_HEADERS =
            "ProductRestController.createProduct() called with headers: {}";
    public static final String CREATE_PRODUCT_CALLED_WITH_PRODUCT_REQUEST =
            "ProductRestController.createProduct() called with productRequest: {}";
    public static final String UPDATE_PRODUCT_CALLED_WITH_HEADERS =
            "ProductRestController.updateProduct() called with headers: {}";
    public static final String UPDATE_PRODUCT_CALLED_WITH_PRODUCT_ID =
            "ProductRestController.updateProduct() called with productId: {}";
    public static final String UPDATE_PRODUCT_CALLED_WITH_PRODUCT_REQUEST =
            "ProductRestController.updateProduct() called with productRequest: {}";
    public static final String DELETE_PRODUCT_CALLED_WITH_HEADERS = "ProductRestController.deleteProduct() called with headers: {}";
    public static final String DELETE_PRODUCT_CALLED_WITH_PRODUCT_ID = "ProductRestController.deleteProduct() called with productId: {}";

    private final ProductService productService;
    private final AuthenticationHelper authenticationHelper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {

        log.info(GET_PRODUCTS_CALLED);

        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int productId) {

        log.info(GET_PRODUCT_WITH_CALLED_WITH_PRODUCT_ID, productId);

        ProductDTO product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody ProductRequest productRequest) {

        log.info(CREATE_PRODUCT_CALLED_WITH_HEADERS, headers);
        log.info(CREATE_PRODUCT_CALLED_WITH_PRODUCT_REQUEST, productRequest);

        ProductDTO createdProduct = productService.createProduct(headers, productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestHeader HttpHeaders headers,
            @PathVariable int productId,
            @Valid @RequestBody ProductRequest productRequest) {

        log.info(UPDATE_PRODUCT_CALLED_WITH_HEADERS, headers);
        log.info(UPDATE_PRODUCT_CALLED_WITH_PRODUCT_ID, productId);
        log.info(UPDATE_PRODUCT_CALLED_WITH_PRODUCT_REQUEST, productRequest);

        ProductDTO updatedProduct = productService.updateProduct(headers, productId, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader HttpHeaders headers,
            @PathVariable int productId) {

        log.info(DELETE_PRODUCT_CALLED_WITH_HEADERS, headers);
        log.info(DELETE_PRODUCT_CALLED_WITH_PRODUCT_ID, productId);

        productService.deleteProduct(headers, productId);
        return new ResponseEntity<>(DELETE_PRODUCT_MSG, HttpStatus.OK);
    }
}
