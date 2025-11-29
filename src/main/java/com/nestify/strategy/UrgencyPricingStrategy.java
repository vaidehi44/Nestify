package com.nestify.strategy;

import com.nestify.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy {

    private final PricingStrategy pricingStrategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);

        //Increase inventory price if date is within week from today
        LocalDate today = LocalDate.now();
        if (!inventory.getDate().isBefore(today) &&
                inventory.getDate().isBefore(today.plusDays(7))) {
            return price.multiply(BigDecimal.valueOf(1.35));
        }
        return price;
    }
}
