package com.groceryshop.groceryshop.dtos;

import lombok.Builder;

@Builder
public record ProductDTO(
        String name,
        int price
) {
}
