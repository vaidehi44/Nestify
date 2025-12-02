package com.nestify.service;

import com.nestify.entity.Inventory;
import com.nestify.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PriceUpdateService {

    private final PricingService pricingService;
    private final InventoryRepository inventoryRepository;

    //@Scheduled(cron = "*/20 * * * * ?")
    @Scheduled(cron = "0 0 * * * *")
    public void updateInventoryPrices() {
        log.info("Updating Inventory prices as per pricing strategy");

        int page = 0;
        int batchSize = 100;
        while (true) {
            Page<Inventory> inventoryPage = inventoryRepository
                    .findAll(PageRequest.of(page, batchSize));

            if (inventoryPage.isEmpty()) break;

            //Update price as per pricing strategy
            inventoryPage
                    .getContent()
                    .forEach(inventory -> {
                        BigDecimal updatedPrice = pricingService.calculatePrice(inventory);
                        inventory.setPrice(updatedPrice);
                    });
            page++;
        }
    }



}
