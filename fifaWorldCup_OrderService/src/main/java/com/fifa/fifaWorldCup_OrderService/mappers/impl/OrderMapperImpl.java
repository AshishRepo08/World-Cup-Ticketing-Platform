package com.fifa.fifaWorldCup_OrderService.mappers.impl;

import com.fifa.fifaWorldCup_OrderService.dto.OrderConfirmationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequestDto;
import com.fifa.fifaWorldCup_OrderService.entity.Order;
import com.fifa.fifaWorldCup_OrderService.event.OrderPlacedEvent;
import com.fifa.fifaWorldCup_OrderService.mappers.OrderMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order fromOrderRequest(OrderRequestDto orderRequest) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        return order;
    }

    @Override
    public OrderConfirmationResponse toOrderConfirmationResponse(Order order) {
        OrderConfirmationResponse orderConfirmationResponse = OrderConfirmationResponse.builder()
                .orderNumber(order.getOrderNumber())
                .skuCode(order.getSkuCode())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status("PLACED")
                .message("Order placed successfully")
                .build();

        return orderConfirmationResponse;
    }

    @Override
    public OrderPlacedEvent toOrderPlacedEvent(Order newOrder, OrderRequestDto orderRequest) {
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderNumber(newOrder.getOrderNumber());
        orderPlacedEvent.setEmail(orderRequest.userDetails().email());
        orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
        orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());

        return orderPlacedEvent;
    }
}
