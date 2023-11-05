package com.groceryshop.groceryshop.repositories.impls;

import com.groceryshop.groceryshop.models.ProductEntity;
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
    public List<ProductEntity> selectAllProducts() {
        Page<ProductEntity> pageProducts = productRepository.findAll(Pageable.ofSize(30));
        return pageProducts.getContent();
    }

    @Override
    public Optional<ProductEntity> selectProductById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<ProductEntity> selectProductByName(String name) {
        return productRepository.findProductEntityByName(name);
    }

    @Override
    public void insertProduct(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Override
    public void updateProduct(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Override
    public void deleteProduct(ProductEntity productEntity) {
        productRepository.delete(productEntity);
    }
}
