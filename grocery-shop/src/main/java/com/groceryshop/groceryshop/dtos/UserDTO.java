package com.groceryshop.groceryshop.dtos;

import lombok.Builder;

@Builder
public record UserDTO(
        int id,
        String username,
        String firstname,
        String lastName
) {
}
