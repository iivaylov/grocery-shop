package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TwoForThreeDeal implements DealStrategy {
    private static final int MIN_PRODUCTS_FOR_DEAL = 3;
    private final List<Product> eligibleProducts;

    public TwoForThreeDeal(List<Product> eligibleProducts) {
        this.eligibleProducts = eligibleProducts;
    }

    @Override
    public void applyDeal(
            List<Product> products,
            Map<Product, Integer> priceList,
            Map<Product, Integer> productCount) {

        PriorityQueue<Product> eligibleProductsQueue = new PriorityQueue<>(Comparator.comparingInt(priceList::get));

        for (Product product : products) {
            if (eligibleProducts.contains(product)) {
                eligibleProductsQueue.offer(product);
            }
        }

        while (eligibleProductsQueue.size() >= MIN_PRODUCTS_FOR_DEAL) {
            eligibleProductsQueue.poll();
            productCount.put(eligibleProductsQueue.poll(), productCount.get(eligibleProductsQueue.poll()) - 1);
            productCount.put(eligibleProductsQueue.poll(), productCount.get(eligibleProductsQueue.poll()) - 1);
        }
    }
}
