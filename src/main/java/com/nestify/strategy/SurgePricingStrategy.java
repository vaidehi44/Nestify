package com.nestify.strategy;

import com.nestify.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {

    private final PricingStrategy pricingStrategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
