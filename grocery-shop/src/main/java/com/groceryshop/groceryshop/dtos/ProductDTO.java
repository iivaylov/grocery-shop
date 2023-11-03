package com.groceryshop.groceryshop.dtos;

import com.groceryshop.groceryshop.models.Product;

public record ProductDTO(
        String name,
        int price
) {
    public Product toProduct() {
        return Product.builder()
                .name(name)
                .price(price)
                .build();
    }
}
