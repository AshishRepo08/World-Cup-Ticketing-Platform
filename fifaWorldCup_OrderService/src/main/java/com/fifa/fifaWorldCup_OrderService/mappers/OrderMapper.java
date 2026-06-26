package com.fifa.fifaWorldCup_OrderService.mappers;

import com.fifa.fifaWorldCup_OrderService.dto.OrderConfirmationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequest;
import com.fifa.fifaWorldCup_OrderService.entity.Order;
import com.fifa.fifaWorldCup_OrderService.event.OrderPlacedEvent;

public interface OrderMapper {
    Order fromOrderRequest(OrderRequest orderRequest);

    OrderConfirmationResponse toOrderConfirmationResponse(Order order);

    OrderPlacedEvent toOrderPlacedEvent(Order order, OrderRequest orderRequest);
}
