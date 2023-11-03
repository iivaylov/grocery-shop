package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class TwoForThreeDeal implements DealStrategy {

    public static final int THREE_PRODUCTS = 3;

    private final List<Product> eligibleProducts = new ArrayList<>();

    @Override
    public int applyDeals(List<Product> shoppingList) {
        Map<Product, Integer> productFrequency = new HashMap<>();

        for (Product product : shoppingList) {
            if (eligibleProducts.contains(product)) {
                productFrequency.put(product, productFrequency.getOrDefault(product, 0) + 1);
            }
        }

        int totalDiscount = 0;

        for (Product product : eligibleProducts) {
            Integer count = productFrequency.getOrDefault(product, 0);
            if (count >= THREE_PRODUCTS) {
                int dealCount = count / THREE_PRODUCTS;
                int discountAmount = product.getPrice() * dealCount;
                totalDiscount += discountAmount;
                productFrequency.put(product, count - dealCount * THREE_PRODUCTS);
            }
        }

        return totalDiscount;
    }

    @Override
    public void initialize() {
        //TODO - hard coded products
        eligibleProducts.add(new Product("apple", 50));
        eligibleProducts.add(new Product("banana", 40));
        eligibleProducts.add(new Product("tomato", 30));
    }
}

