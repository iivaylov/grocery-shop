package com.groceryshop.groceryshop.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank.")
    @Size(min = 2, max = 20, message = "Product name should be between 2 and 20 symbols.")
    private String name;

    @NotNull(message = "Product price cannot be blank.")
    @Positive(message = "Product price should be positive.")
    private int price;
}
