package com.groceryshop.groceryshop.repositories.impls;

import com.groceryshop.groceryshop.models.Product;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.repositories.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductJPADataAccessService implements ProductDAO {

    private final ProductRepository productRepository;

    @Autowired
    public ProductJPADataAccessService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> selectAllProducts() {
        Page<Product> pageProducts = productRepository.findAll(Pageable.ofSize(30));
        return pageProducts.getContent();
    }

    @Override
    public Optional<Product> selectProductById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<Product> selectProductByName(String name) {
        return Optional.empty();
    }

    @Override
    public void insertProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}
