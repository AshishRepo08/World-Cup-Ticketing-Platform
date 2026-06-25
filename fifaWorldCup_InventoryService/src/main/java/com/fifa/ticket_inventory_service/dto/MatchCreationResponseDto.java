package com.fifa.ticket_inventory_service.dto;

public record MatchCreationResponseDto(Long id, String skuCode, Integer totalTickets, Integer ticketsLeft) { }
