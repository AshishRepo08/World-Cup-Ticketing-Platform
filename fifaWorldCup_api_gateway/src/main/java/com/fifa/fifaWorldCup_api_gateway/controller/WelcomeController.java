package com.fifa.fifaWorldCup_api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome To Ticket Gateway";
    }
}
