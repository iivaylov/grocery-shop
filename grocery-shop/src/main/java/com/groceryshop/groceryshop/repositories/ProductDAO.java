package com.groceryshop.groceryshop.repositories;

import com.groceryshop.groceryshop.models.Product;

import java.util.Optional;

public interface ProductDAO {

    Optional<Product> selectProductById(int productId);

    void insertProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);
}
