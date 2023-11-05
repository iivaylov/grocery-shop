package com.groceryshop.groceryshop.repositories.jpa;

import com.groceryshop.groceryshop.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findProductEntityByName(String name);
}
