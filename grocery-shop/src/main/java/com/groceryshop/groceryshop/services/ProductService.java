package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(int productId);

//    ProductDTO getProductByName(String name);

    ProductDTO createProduct(ProductRequest productRequest, UserDTO userDTO);

    ProductDTO updateProduct(ProductRequest productRequest, UserDTO userDTO);

    void deleteProduct(int productId, UserDTO userDTO);


}
