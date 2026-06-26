package com.fifa.fifaWorldCup_OrderService.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderConfirmationResponse (

        String orderNumber,
        String skuCode,
        Integer quantity,
        BigDecimal price,
        String status,
        String message
) {
}
