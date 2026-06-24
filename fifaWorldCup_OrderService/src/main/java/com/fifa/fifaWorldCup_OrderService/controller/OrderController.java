package com.fifa.fifaWorldCup_OrderService.controller;

import com.fifa.fifaWorldCup_OrderService.dto.OrderRequest;
import com.fifa.fifaWorldCup_OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("In Controller: "+orderRequest);
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

}
