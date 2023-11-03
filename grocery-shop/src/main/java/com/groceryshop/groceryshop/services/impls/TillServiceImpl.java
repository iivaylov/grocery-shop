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
import java.util.stream.Collectors;

@Service
public class TillServiceImpl implements TillService {

    private final Map<Product, Integer> priceList = new HashMap<>();
    private final List<DealStrategy> dealStrategies = new ArrayList<>();

    @Override
    public String calculateUserBill(List<ProductDTO> productsDTOs) {
        List<Product> products = productsDTOs.stream()
                .map(ProductDTO::toProduct)
                .collect(Collectors.toList());

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

        int aws = result / 100;
        int clouds = result % 100;
        return String.format("Total bill: %s aws and %s clouds", aws, clouds);
    }

    public void addDeal(DealStrategy dealStrategy) {
        dealStrategies.add(dealStrategy);
    }
}
