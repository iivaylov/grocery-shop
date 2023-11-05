package com.groceryshop.groceryshop.services.impls;

import com.groceryshop.groceryshop.dtos.ProductDTO;
import com.groceryshop.groceryshop.models.ProductEntity;
import com.groceryshop.groceryshop.repositories.ProductDAO;
import com.groceryshop.groceryshop.services.TillService;
import com.groceryshop.groceryshop.services.UserService;
import com.groceryshop.groceryshop.services.deals.DealStrategyHandler;
import com.groceryshop.groceryshop.utils.AuthenticationHelper;
import lombok.Data;
import org.springframework.http.HttpHeaders;
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
    public static final int CLOUDS_THRESHOLD = 100;

    private final List<DealStrategyHandler> dealStrategies;
    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;
    private final ProductDAO productDAO;

    @Override
    public String calculateUserBill(HttpHeaders headers) {
        List<ProductDTO> userShoppingList = userService.getUserShoppingList(headers);

        List<String> productNames = userShoppingList.stream()
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

    private String calculateUserBillWithDeals(List<ProductEntity> productEntities) {
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

    private static String calculateResult(int result) {
        if (result < CLOUDS_THRESHOLD) {
            return formatToBillClouds(result);
        } else {
            int aws = result / CLOUDS_THRESHOLD;
            int clouds = result % CLOUDS_THRESHOLD;
            return formatToBillAwsClouds(aws, clouds);
        }
    }

    private static String formatToBillClouds(int result) {
        return TOTAL_BILL_CLOUDS.formatted(result);
    }

    private static String formatToBillAwsClouds(int aws, int clouds) {
        String cloudsStr = clouds < 10 ? "0" + clouds : String.valueOf(clouds);
        return TOTAL_BILL_AWS_CLOUDS.formatted(aws, cloudsStr);
    }
}