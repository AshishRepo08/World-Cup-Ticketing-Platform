package com.fifa.fifaWorldCup_NotificationService.event;

import lombok.Data;
import lombok.ToString;

@Data
public class OrderPlacedEvent {
    private String orderNumber;
    private String email;
    private String firstName;
    private String lastName;
}
