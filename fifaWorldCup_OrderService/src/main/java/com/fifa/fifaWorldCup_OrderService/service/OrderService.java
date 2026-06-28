package com.fifa.fifaWorldCup_OrderService.service;

import com.fifa.fifaWorldCup_OrderService.client.InventoryServiceClient;
import com.fifa.fifaWorldCup_OrderService.customExceptions.OutOfStockException;
import com.fifa.fifaWorldCup_OrderService.dto.InventoryReservationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderConfirmationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequestDto;
import com.fifa.fifaWorldCup_OrderService.entity.Order;
import com.fifa.fifaWorldCup_OrderService.event.OrderPlacedEvent;
import com.fifa.fifaWorldCup_OrderService.mappers.OrderMapper;
import com.fifa.fifaWorldCup_OrderService.repository.OrderRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final OrderMapper orderMapper;

    @Transactional
    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallBack")
    public OrderConfirmationResponse placeOrder(OrderRequestDto orderRequestDto) {
        log.info("Order placement started. skuCode={}, quantity={}",
                orderRequestDto.skuCode(), orderRequestDto.quantity());

        InventoryReservationResponse reservationResponse =
                inventoryServiceClient.reserveTickets(
                        orderRequestDto.skuCode(),
                        orderRequestDto.quantity()
                );

        if (reservationResponse == null ||
                !"RESERVED".equalsIgnoreCase(reservationResponse.status())) {

            throw new OutOfStockException(
                    "Tickets are not available for skuCode: " + orderRequestDto.skuCode()
            );
        }

        Order newOrder = orderMapper.fromOrderRequest(orderRequestDto);

        Order savedOrder = orderRepository.save(newOrder);

        OrderPlacedEvent currentOrderEvent =
                orderMapper.toOrderPlacedEvent(savedOrder, orderRequestDto);

        log.info("Sending OrderPlacedEvent to Kafka. orderNumber={}",
                savedOrder.getOrderNumber());

        kafkaTemplate.send("order-placed", currentOrderEvent);

        log.info("OrderPlacedEvent sent successfully. orderNumber={}",
                savedOrder.getOrderNumber());

        return orderMapper.toOrderConfirmationResponse(savedOrder);
    }

    public OrderConfirmationResponse createOrderFallBack(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Fallback occurred due to : {}", throwable.getMessage());
        return new OrderConfirmationResponse(null, null, null, null, "FAILED", "Order couldn't be placed. Please try again after some time.");
    }

    //For Reference - Below are logs for fallback method implementation
    /*
        In Controller: OrderRequestDto[skuCode=Spain vs Saudi Arabia, price=1000, quantity=2, userDetails=UserDetails[email=first@gmail.com, firstName=first, lastName=user]]
        2026-06-28T12:17:25.978+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.service.OrderService               : Order placement started. skuCode=Spain vs Saudi Arabia, quantity=2
        2026-06-28T12:17:25.979+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.client.InventoryServiceClient      : Calling Inventory Service to reserve tickets. skuCode=Spain vs Saudi Arabia, quantity=2

        2026-06-28T12:17:27.076+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.service.OrderService               : Order placement started. skuCode=Spain vs Saudi Arabia, quantity=2
        2026-06-28T12:17:27.077+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.client.InventoryServiceClient      : Calling Inventory Service to reserve tickets. skuCode=Spain vs Saudi Arabia, quantity=2

        2026-06-28T12:17:28.101+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.service.OrderService               : Order placement started. skuCode=Spain vs Saudi Arabia, quantity=2
        2026-06-28T12:17:28.102+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.client.InventoryServiceClient      : Calling Inventory Service to reserve tickets. skuCode=Spain vs Saudi Arabia, quantity=2

        2026-06-28T12:17:29.126+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.service.OrderService               : Order placement started. skuCode=Spain vs Saudi Arabia, quantity=2
        2026-06-28T12:17:29.127+05:30  INFO 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.client.InventoryServiceClient      : Calling Inventory Service to reserve tickets. skuCode=Spain vs Saudi Arabia, quantity=2

        2026-06-28T12:17:29.141+05:30 ERROR 32376 --- [fifaWorldCup_OrderService] [nio-9001-exec-1] c.f.f.service.OrderService               : Fallback occurred due to : I/O error on POST request for "http://localhost:8082/inventory/reserve": null
     */
}
