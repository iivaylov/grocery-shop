package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.models.enums.DealEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Getter
@Setter
public class TwoForThreeDealHandler implements DealStrategyHandler {

    public static final int THREE_PRODUCTS = 3;

    @Override
    public int getDiscountedAmount(List<ProductEntity> shoppingList) {
        List<ProductEntity> productsWithDeal = new ArrayList<>();

        for (ProductEntity productEntity : shoppingList) {

            boolean containsDeal = productEntity.getDealEntity()
                    .stream()
                    .anyMatch(dealEntity -> dealEntity.getDealEnum().equals(DealEnum.TWO_FOR_THREE));

            if (containsDeal) {
                productsWithDeal.add(productEntity);
            }
        }

        int discountedProducts = productsWithDeal.size() / THREE_PRODUCTS;

        return productsWithDeal.stream()
                .map(ProductEntity::getPrice)
                .sorted(Comparator.comparingInt(price -> price))
                .limit(discountedProducts)
                .reduce(0, Integer::sum);
    }
}

