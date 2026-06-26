package com.fifa.fifaWorldCup_OrderService.service;

import com.fifa.fifaWorldCup_OrderService.client.InventoryServiceClient;
import com.fifa.fifaWorldCup_OrderService.customExceptions.OutOfStockException;
import com.fifa.fifaWorldCup_OrderService.dto.InventoryReservationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderConfirmationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequest;
import com.fifa.fifaWorldCup_OrderService.entity.Order;
import com.fifa.fifaWorldCup_OrderService.event.OrderPlacedEvent;
import com.fifa.fifaWorldCup_OrderService.mappers.OrderMapper;
import com.fifa.fifaWorldCup_OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderConfirmationResponse placeOrder(OrderRequest orderRequest) {
        log.info("Order placement started. skuCode={}, quantity={}", orderRequest.skuCode(), orderRequest.quantity());

        var isProductInStock = inventoryServiceClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(!isProductInStock) {
            throw new OutOfStockException("Product with skuCode " + orderRequest.skuCode() + " is not in the stock");
        }

        InventoryReservationResponse reservationResponse = inventoryServiceClient.reserveTickets(
                orderRequest.skuCode(),
                orderRequest.quantity());

        if (reservationResponse == null || !"RESERVED".equalsIgnoreCase(reservationResponse.status())) {
            throw new OutOfStockException(
                    "Tickets are not available for skuCode: " + orderRequest.skuCode()
            );
        }

        //Create Order
        Order newOrder = orderMapper.fromOrderRequest(orderRequest);
        orderRepository.save(newOrder);


        //Create A Event
        OrderPlacedEvent currentOrderEvent = orderMapper.toOrderPlacedEvent(newOrder, orderRequest);
        log.info("Start - Sending OrderPlacedEvent {} to kafka topic order-placed", currentOrderEvent);
        kafkaTemplate.send("order-placed", currentOrderEvent);
        log.info("End - Sending OrderPlacedEvent {} to kafka topic order-placed", currentOrderEvent);

        return orderMapper.toOrderConfirmationResponse(newOrder);
    }
}
