package com.groceryshop.groceryshop.services.mappers;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.models.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<ProductEntity, ProductDTO> {
    @Override
    public ProductDTO apply(ProductEntity productEntity) {
        return new ProductDTO(
                productEntity.getName(),
                productEntity.getPrice()
        );
    }
}
