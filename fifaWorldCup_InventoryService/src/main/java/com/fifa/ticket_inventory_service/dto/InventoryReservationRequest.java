package com.fifa.ticket_inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryReservationRequest(@NotBlank String skuCode,
                                          @NotNull @Positive Integer ticketsNeeded
) {}
