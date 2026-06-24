package com.fifa.fifaWorldCup_OrderService.event;

import lombok.Data;

@Data
public class OrderPlacedEvent {
    private String orderNumber;
    private String email;
    private String firstName;
    private String lastName;
}
