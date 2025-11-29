package com.nestify.strategy;

import com.nestify.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy {

    private final PricingStrategy pricingStrategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);

        double occupancyRate = (double) inventory.getBookedCount()
                /inventory.getTotalCount();

        if (occupancyRate > 0.80) {
            return price.multiply(BigDecimal.valueOf(1.50));
        }
        return price;
    }
}
