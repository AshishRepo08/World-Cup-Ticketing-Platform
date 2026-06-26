package com.fifa.ticket_inventory_service.dto;

public record InventoryReservationResponse(String skuCode,
                                           Integer ticketsReserved,
                                           Integer ticketsLeft,
                                           String status
) {}
