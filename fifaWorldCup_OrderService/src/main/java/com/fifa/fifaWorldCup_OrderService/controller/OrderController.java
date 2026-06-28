package com.fifa.fifaWorldCup_OrderService.controller;

import com.fifa.fifaWorldCup_OrderService.dto.OrderConfirmationResponse;
import com.fifa.fifaWorldCup_OrderService.dto.OrderRequestDto;
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
    public OrderConfirmationResponse placeOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        System.out.println("In Controller: "+orderRequestDto);

        OrderConfirmationResponse confirmationResponse = orderService.placeOrder(orderRequestDto);
        return confirmationResponse;
    }

}
