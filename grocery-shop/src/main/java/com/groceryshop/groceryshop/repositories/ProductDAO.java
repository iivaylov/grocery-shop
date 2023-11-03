package com.groceryshop.groceryshop.repositories;

import com.groceryshop.groceryshop.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    List<Product> selectAllProducts();

    Optional<Product> selectProductById(int productId);

    Optional<Product> selectProductByName(String name);

    void insertProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);
}
