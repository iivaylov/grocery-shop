package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.Product;

import java.util.List;
import java.util.Map;

public class BuyOneGetOneHalfPriceDeal implements DealStrategy {

    private static final int HALF_PRICE_RATIO = 2;

    private final Product eligibleProducts;

    public BuyOneGetOneHalfPriceDeal(Product eligibleProducts) {
        this.eligibleProducts = eligibleProducts;
    }

    @Override
    public void applyDeal(List<Product> products,
                          Map<Product, Integer> priceList,
                          Map<Product, Integer> productCount) {
        int fullPriceCount = productCount.getOrDefault(eligibleProducts, 0);
        int halfPriceCount = fullPriceCount / HALF_PRICE_RATIO;

        productCount.put(eligibleProducts, fullPriceCount - halfPriceCount);
    }
}
