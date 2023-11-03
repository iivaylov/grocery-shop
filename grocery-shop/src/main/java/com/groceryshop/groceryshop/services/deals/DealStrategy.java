package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.Product;

import java.util.List;
import java.util.Map;

public interface DealStrategy {

    void applyDeal(List<Product> products,
                   Map<Product, Integer> priceList,
                   Map<Product, Integer> productCount);
}
