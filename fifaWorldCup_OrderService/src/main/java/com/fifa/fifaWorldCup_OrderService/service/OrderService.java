package com.fifa.fifaWorldCup_OrderService.service;

import com.fifa.fifaWorldCup_OrderService.client.InventoryServiceClient;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequest;
import com.fifa.fifaWorldCup_OrderService.entity.Order;
import com.fifa.fifaWorldCup_OrderService.event.OrderPlacedEvent;
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

    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryServiceClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();

            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);


            //Create A Event
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());

            log.info("Start - Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);

            return order;
        }
        else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in the stock");
        }

    }
}
