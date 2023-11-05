package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(int productId);

    ProductDTO createProduct(HttpHeaders headers, ProductRequest productRequest);

    ProductDTO updateProduct(HttpHeaders headers, int productId, ProductRequest productRequest);

    void deleteProduct(HttpHeaders headers, int productId);


}
