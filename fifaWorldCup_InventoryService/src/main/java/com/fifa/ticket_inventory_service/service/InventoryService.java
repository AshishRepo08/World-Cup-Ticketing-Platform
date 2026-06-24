package com.fifa.ticket_inventory_service.service;

import com.fifa.ticket_inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer ticketsNeeded) {
        System.out.println("In Stock Service Method");
        return inventoryRepository.existsBySkuCodeAndTicketsLeftGreaterThanEqual(skuCode, ticketsNeeded);
    }
}
