package com.fifa.fifaWorldCup_OrderService.controller;

import com.fifa.fifaWorldCup_OrderService.dto.OrderConfirmationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequest;
import com.fifa.fifaWorldCup_OrderService.entity.Order;
import com.fifa.fifaWorldCup_OrderService.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderConfirmationResponse placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        System.out.println("In Controller: "+orderRequest);

        OrderConfirmationResponse confirmationResponse = orderService.placeOrder(orderRequest);
        return confirmationResponse;
    }

}
