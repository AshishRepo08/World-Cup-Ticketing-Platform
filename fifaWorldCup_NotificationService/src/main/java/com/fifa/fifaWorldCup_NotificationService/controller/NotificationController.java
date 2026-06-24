package com.fifa.fifaWorldCup_NotificationService.controller;

import com.fifa.fifaWorldCup_NotificationService.event.OrderPlacedEvent;
import com.fifa.fifaWorldCup_NotificationService.service.NotificationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public String sendEmail(@RequestBody OrderPlacedEvent orderPlacedEvent) {
        System.out.println(orderPlacedEvent);
        notificationService.listen(orderPlacedEvent);
        log.info("Email Sent");
        return "Email Sent";
    }

    @GetMapping
    public String hello() {
        return "Welcome To Notification Service";
    }
}
