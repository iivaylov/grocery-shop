package com.groceryshop.groceryshop.repositories;

import com.groceryshop.groceryshop.models.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    List<ProductEntity> selectAllProducts();

    Optional<ProductEntity> selectProductById(int productId);

    Optional<ProductEntity> selectProductByName(String name);

    void insertProduct(ProductEntity productEntity);

    void updateProduct(ProductEntity productEntity);

    void deleteProduct(ProductEntity productEntity);
}
