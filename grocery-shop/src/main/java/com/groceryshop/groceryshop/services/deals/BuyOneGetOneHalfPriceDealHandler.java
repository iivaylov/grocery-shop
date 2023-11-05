package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.models.enums.DealEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class BuyOneGetOneHalfPriceDealHandler implements DealStrategyHandler {

    public static final int HALF_PRICE = 2;

    public static final int TWO_PRODUCTS = 2;

    @Override
    public int getDiscountedAmount(List<ProductEntity> shoppingList) {
        Map<ProductEntity, Integer> productsWithDeal = new HashMap<>();

        for (ProductEntity productEntity : shoppingList) {

            boolean containsDeal = productEntity.getDealEntity()
                    .stream()
                    .anyMatch(dealEntity -> dealEntity.getDealEnum().equals(DealEnum.BUY_ONE_GET_ONE_HALF_PRICE));

            if (containsDeal) {
                productsWithDeal.put(productEntity, productsWithDeal.getOrDefault(productEntity, 0) + 1);
            }
        }

        int discountAmount = 0;

        for (Map.Entry<ProductEntity, Integer> productEntityIntegerEntry : productsWithDeal.entrySet()) {
            int discountedProducts = getHalfAmountProducts(productEntityIntegerEntry);
            discountAmount += discountedProducts * (productEntityIntegerEntry.getKey().getPrice() / HALF_PRICE);
        }

        return discountAmount;
    }

    private static int getHalfAmountProducts(Map.Entry<ProductEntity, Integer> productEntityIntegerEntry) {
        return productEntityIntegerEntry.getValue() / 2;
    }
}
