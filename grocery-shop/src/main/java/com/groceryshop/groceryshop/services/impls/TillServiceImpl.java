package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.services.deals.DealStrategyHandler;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class TillServiceImpl implements TillService {

    public static final String TOTAL_BILL_AWS_CLOUDS = "Total bill: %s aws and %s clouds";
    public static final String NO_PRODUCTS = "No products selected.";
    public static final int MIN_PRODUCTS = 3;
    public static final String TOTAL_BILL_CLOUDS = "Total bill: %s clouds";
    public static final int ONE_HUNDRED_CLOUDS = 100;

    private final List<DealStrategyHandler> dealStrategies;
    private final ProductDAO productDAO;

    private static String calculateResult(int result) {
        if (result < ONE_HUNDRED_CLOUDS) {
            return TOTAL_BILL_CLOUDS.formatted(result);
        } else {
            int aws = result / 100;
            int clouds = result % 100;
            return TOTAL_BILL_AWS_CLOUDS.formatted(aws, clouds);
        }
    }

    @Override
    public String calculateUserBill(List<ProductDTO> productsDTOs) {
        List<String> productNames = productsDTOs.stream()
                .map(ProductDTO::name)
                .toList();

        List<ProductEntity> productEntities = new ArrayList<>();

        for (String productName : productNames) {
            Optional<ProductEntity> productEntity = productDAO.selectProductByName(productName);

            productEntity.ifPresent(productEntities::add);
        }

        if (productEntities.isEmpty()) {
            return NO_PRODUCTS;
        }

        if (productEntities.size() < MIN_PRODUCTS) {
            int result = productEntities.stream()
                    .mapToInt(ProductEntity::getPrice)
                    .sum();
            return calculateResult(result);
        }

        return calculateUserBillWithDeals(productEntities);
    }

    public String calculateUserBillWithDeals(List<ProductEntity> productEntities) {
        List<ProductEntity> shoppingList = new ArrayList<>(productEntities);
        int totalDiscount = 0;

        for (DealStrategyHandler dealStrategyHandler : dealStrategies) {
            totalDiscount += dealStrategyHandler.getDiscountedAmount(shoppingList);
        }

        int totalAmountBeforeDiscount = shoppingList
                .stream()
                .mapToInt(ProductEntity::getPrice)
                .sum();

        int finalAmount = totalAmountBeforeDiscount - totalDiscount;

        return calculateResult(finalAmount);
    }
}