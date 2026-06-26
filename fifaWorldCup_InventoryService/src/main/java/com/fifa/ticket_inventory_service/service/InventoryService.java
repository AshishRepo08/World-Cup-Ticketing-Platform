package com.fifa.ticket_inventory_service.service;

import com.fifa.ticket_inventory_service.dto.InventoryReservationRequest;
import com.fifa.ticket_inventory_service.dto.InventoryReservationResponse;
import com.fifa.ticket_inventory_service.dto.MatchCreationDto;
import com.fifa.ticket_inventory_service.dto.MatchCreationResponseDto;
import com.fifa.ticket_inventory_service.entity.Match;
import com.fifa.ticket_inventory_service.repository.InventoryRepository;
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
    @Version
    public InventoryReservationResponse reserveTicket(InventoryReservationRequest reservationRequest) {
        Match requestedMatch = inventoryRepository.findBySkuCode(reservationRequest.skuCode());
        log.info("Match Pre Ticket Reduction : "+requestedMatch);

        requestedMatch.setTicketsLeft(requestedMatch.getTicketsLeft() - reservationRequest.ticketsNeeded());
        log.info("Match Post Ticket Reduction : "+requestedMatch);

        inventoryRepository.save(requestedMatch);

        InventoryReservationResponse reservationResponse = new InventoryReservationResponse(requestedMatch.getSkuCode(), reservationRequest.ticketsNeeded(), requestedMatch.getTicketsLeft(), "RESERVED");
        return reservationResponse;
    }
}
