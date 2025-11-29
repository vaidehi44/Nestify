package com.nestify.service;

import com.nestify.entity.Inventory;
import com.nestify.strategy.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculatePrice(Inventory inventory) {
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }
}
