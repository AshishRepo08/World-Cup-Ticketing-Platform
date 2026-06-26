package com.fifa.fifaWorldCup_OrderService.dto;

public record InventoryReservationResponse(String skuCode,
                                           Integer ticketsReserved,
                                           Integer ticketsLeft,
                                           String status) {
}
