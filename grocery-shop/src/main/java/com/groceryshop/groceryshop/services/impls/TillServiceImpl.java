package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.models.Product;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.services.deals.DealStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TillServiceImpl implements TillService {

    public static final String TOTAL_RESULT_MSG = "Total bill: %s aws and %s clouds";
    public static final String NO_PRODUCTS = "No products selected.";
    public static final int MIN_PRODUCTS = 3;
    private final Map<Product, Integer> priceList = new HashMap<>();
    private final List<DealStrategy> dealStrategies = new ArrayList<>();

    @Override
    public String calculateUserBill(List<ProductDTO> productsDTOs) {
        List<Product> products = productsDTOs.stream()
                .map(ProductDTO::toProduct)
                .toList();

        if (products.isEmpty()) {
            return NO_PRODUCTS;
        }

        if (products.size() < MIN_PRODUCTS) {
            int result = products.stream()
                    .mapToInt(priceList::get)
                    .sum();

            return calculateResult(result);
        }

        return summarizePurchaseCost(products);
    }

    private String summarizePurchaseCost(List<Product> products) {
        Map<Product, Integer> productCount = new HashMap<>();
        products.forEach(item -> productCount.put(item, productCount.getOrDefault(item, 0) + 1));

        for (DealStrategy dealStrategy : dealStrategies) {
            dealStrategy.applyDeal(products, priceList, productCount);
        }

        int result = productCount.entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * priceList.get(entry.getKey()))
                .sum();

        return calculateResult(result);
    }

    public void addDeal(DealStrategy dealStrategy) {
        dealStrategies.add(dealStrategy);
    }

    private static String calculateResult(int result) {
        int aws = result / 100;
        int clouds = result % 100;
        return String.format(TOTAL_RESULT_MSG, aws, clouds);
    }
}
