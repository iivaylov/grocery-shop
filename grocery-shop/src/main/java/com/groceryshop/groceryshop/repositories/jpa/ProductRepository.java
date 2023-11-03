package com.groceryshop.groceryshop.repositories.jpa;

import com.groceryshop.groceryshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
