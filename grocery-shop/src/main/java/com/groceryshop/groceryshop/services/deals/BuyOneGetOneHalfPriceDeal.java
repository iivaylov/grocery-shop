package com.groceryshop.groceryshop.services.deals;

import com.groceryshop.groceryshop.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class BuyOneGetOneHalfPriceDeal implements DealStrategy {

    public static final int HALF_PRICE = 2;

    public static final int TWO_PRODUCTS = 2;

    private Product eligibleProduct;

    @Override
    public int applyDeals(List<Product> shoppingList) {
        int count = 0;
        for (Product product : shoppingList) {
            if (product.equals(eligibleProduct)) {
                count++;
            }
        }

        int discountAmount = 0;
        while (count >= HALF_PRICE) {
            discountAmount += eligibleProduct.getPrice() / HALF_PRICE;
            count -= HALF_PRICE;
        }

        return discountAmount;
    }

    @Override
    public void initialize() {
        //TODO - hard coded product
        eligibleProduct = new Product("potato", 26);
    }
}
