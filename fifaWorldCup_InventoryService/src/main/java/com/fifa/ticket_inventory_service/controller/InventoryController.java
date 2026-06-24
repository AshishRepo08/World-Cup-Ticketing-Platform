package com.fifa.ticket_inventory_service.controller;

import com.fifa.ticket_inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer ticketsNeeded) {
        System.out.println("In Stock Controller Method");
        return inventoryService.isInStock(skuCode, ticketsNeeded);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome To Inventory Service";
    }

}
