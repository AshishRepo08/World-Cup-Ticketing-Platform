package com.fifa.ticket_inventory_service.service;

import com.fifa.ticket_inventory_service.dto.InventoryReservationRequest;
import com.fifa.ticket_inventory_service.dto.InventoryReservationResponse;
import com.fifa.ticket_inventory_service.dto.MatchCreationDto;
import com.fifa.ticket_inventory_service.dto.MatchCreationResponseDto;
import com.fifa.ticket_inventory_service.entity.Match;
import com.fifa.ticket_inventory_service.repository.InventoryRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.persistence.Version;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer ticketsNeeded) {
        log.info("In Stock Service Method");
        return inventoryRepository.existsBySkuCodeAndTicketsLeftGreaterThanEqual(skuCode, ticketsNeeded);
    }

    public MatchCreationResponseDto createMatch(MatchCreationDto matchCreationRequest) {

        Match newMatch = Match.builder()
                .skuCode(matchCreationRequest.skuCode())
                .totalTickets(matchCreationRequest.totalTickets())
                .ticketsLeft(matchCreationRequest.totalTickets())
                .build();

        inventoryRepository.save(newMatch);

        MatchCreationResponseDto MatchCreationResponseDto = new MatchCreationResponseDto(newMatch.getId(), newMatch.getSkuCode(), newMatch.getTotalTickets(), newMatch.getTicketsLeft());
        return MatchCreationResponseDto;
    }

    public List<Match> getAllMatches() {
        return inventoryRepository.findAll();
    }

    @Transactional
    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "rateLimiterFallBack")
    public InventoryReservationResponse reserveTicket(InventoryReservationRequest reservationRequest) {
        Match requestedMatch = inventoryRepository.findBySkuCode(reservationRequest.skuCode());

        if (requestedMatch == null) {
            return new InventoryReservationResponse(
                    reservationRequest.skuCode(),
                    0,
                    0,
                    "NOT_FOUND"
            );
        }

        if (requestedMatch.getTicketsLeft() < reservationRequest.ticketsNeeded()) {
            return new InventoryReservationResponse(
                    requestedMatch.getSkuCode(),
                    0,
                    requestedMatch.getTicketsLeft(),
                    "OUT_OF_STOCK"
            );
        }

        requestedMatch.setTicketsLeft(
                requestedMatch.getTicketsLeft() - reservationRequest.ticketsNeeded()
        );

        Match savedMatch = inventoryRepository.save(requestedMatch);

        return new InventoryReservationResponse(
                savedMatch.getSkuCode(),
                reservationRequest.ticketsNeeded(),
                savedMatch.getTicketsLeft(),
                "RESERVED"
        );
    }

    public InventoryReservationResponse rateLimiterFallBack(InventoryReservationRequest reservationRequest, Throwable throwable) {
        log.error("Fallback occurred due to : {}", throwable.getMessage());
        return  new InventoryReservationResponse(null,null,null,null);
    }
}
