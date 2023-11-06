package com.groceryshop.groceryshop;

import com.groceryshop.groceryshop.controllers.requests.ProductRequest;
import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.models.DealEntity;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.models.enums.DealEnum;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class Helpers {

    public static ProductEntity createMockProduct() {
        return ProductEntity.builder()
                .id(1)
                .name("MockProduct")
                .price(10)
                .dealEntity(List.of(createMockDealTwoForThree()))
                .build();
    }

    public static DealEntity createMockDealTwoForThree() {
        return DealEntity.builder()
                .id(1)
                .dealEnum(DealEnum.TWO_FOR_THREE)
                .build();
    }

    public static HttpHeaders createHeaders() {
        return new HttpHeaders();
    }

    public static ProductDTO createMockProductDTO() {
        return ProductDTO.builder()
                .name("MockProduct")
                .price(10)
                .build();
    }

    public static ProductRequest createMockProductRequest() {
        return ProductRequest.builder()
                .name("MockProduct")
                .price(10)
                .build();
    }
}
