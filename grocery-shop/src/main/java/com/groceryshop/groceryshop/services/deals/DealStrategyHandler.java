package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.ProductEntity;

import java.util.List;

public interface DealStrategyHandler {

    int getDiscountedAmount(List<ProductEntity> shoppingList);
}
