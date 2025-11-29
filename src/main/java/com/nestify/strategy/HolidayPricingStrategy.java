package com.nestify.strategy;

import com.nestify.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy {

    private final PricingStrategy pricingStrategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);
        boolean isHoliday = true; //TODO: Add function to check holiday
        if (isHoliday) {
            return price.multiply(BigDecimal.valueOf(1.20));
        }
        return price;
    }
}
