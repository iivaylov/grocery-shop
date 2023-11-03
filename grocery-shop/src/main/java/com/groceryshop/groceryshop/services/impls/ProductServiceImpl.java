package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public ProductDTO getProductById(int productId) {
        return null;
    }

    @Override
    public ProductDTO createProduct(ProductRequest productRequest, UserDTO userDTO) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(ProductRequest productRequest, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteProduct(int productId, UserDTO userDTO) {

    }
}
