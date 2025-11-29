package com.nestify.strategy;

import com.nestify.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    public BigDecimal calculatePrice(Inventory inventory);
}
