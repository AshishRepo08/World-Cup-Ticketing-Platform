package com.fifa.ticket_inventory_service.controller;

import com.fifa.ticket_inventory_service.dto.MatchCreationDto;
import com.fifa.ticket_inventory_service.dto.MatchCreationResponseDto;
import com.fifa.ticket_inventory_service.entity.Match;
import com.fifa.ticket_inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome To Inventory Service";
    }

    @GetMapping
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer ticketsNeeded) {
        System.out.println("In Stock Controller Method");
        Boolean result = inventoryService.isInStock(skuCode, ticketsNeeded);

        //Reserved Tickets
        if(result) {
            inventoryService.reserveTicket(skuCode, ticketsNeeded);
        }

        return result;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatchCreationResponseDto> createMatch(@RequestBody MatchCreationDto matchCreationDto) {
        log.info("New Match Creation Request : "+ matchCreationDto);
        MatchCreationResponseDto newlyCreatedMatchResponse = inventoryService.createMatch(matchCreationDto);

        return new ResponseEntity<>(newlyCreatedMatchResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Match>> getAllMatches() {
        return new ResponseEntity<>(inventoryService.getAllMatches(), HttpStatus.OK);
    }



}
