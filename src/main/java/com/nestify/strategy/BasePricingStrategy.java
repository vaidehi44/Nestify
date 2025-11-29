package com.nestify.strategy;

import com.nestify.entity.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
