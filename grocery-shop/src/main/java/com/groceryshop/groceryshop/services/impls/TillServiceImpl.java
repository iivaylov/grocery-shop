package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.models.Product;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.services.deals.DealStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TillServiceImpl implements TillService {

    public static final String TOTAL_BILL_AWS_CLOUDS = "Total bill: %s aws and %s clouds";
    public static final String NO_PRODUCTS = "No products selected.";
    public static final int MIN_PRODUCTS = 3;
    public static final String TOTAL_BILL_CLOUDS = "Total bill: %s clouds";
    public static final int ONE_HUNDRED_CLOUDS = 100;
    private final List<DealStrategy> dealStrategies;

    @Autowired
    public TillServiceImpl(List<DealStrategy> dealStrategies) {
        this.dealStrategies = dealStrategies;
        initializeDealStrategies();
    }

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
                    .mapToInt(Product::getPrice)
                    .sum();
            return calculateResult(result);
        }

        return calculateUserBillWithDeals(products);
    }

    public String calculateUserBillWithDeals(List<Product> products) {
        List<Product> shoppingList = new ArrayList<>(products);
        int totalDiscount = 0;

        for (DealStrategy dealStrategy : dealStrategies) {
            totalDiscount += dealStrategy.applyDeals(shoppingList);
        }

        int totalAmountBeforeDiscount = shoppingList.stream()
                .mapToInt(Product::getPrice)
                .sum();
        int finalAmount = totalAmountBeforeDiscount - totalDiscount;

        return calculateResult(finalAmount);
    }

    private static String calculateResult(int result) {
        if (result < ONE_HUNDRED_CLOUDS) {
            return TOTAL_BILL_CLOUDS.formatted(result);
        } else {
            int aws = result / 100;
            int clouds = result % 100;
            return TOTAL_BILL_AWS_CLOUDS.formatted(aws, clouds);
        }
    }

    //TODO
    private void initializeDealStrategies() {
        for (DealStrategy dealStrategy : dealStrategies) {
            dealStrategy.initialize();
        }
    }
}