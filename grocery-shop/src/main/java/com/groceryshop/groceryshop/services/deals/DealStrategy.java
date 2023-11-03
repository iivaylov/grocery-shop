package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.Product;

import java.util.List;

public interface DealStrategy {

    int applyDeals(List<Product> shoppingList);

    void initialize();
}
