package com.fifa.fifaWorldCup_OrderService.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${inventoryService.url}")
    private String BASE_URL;

    @Bean
    RestClient getRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeaders(header -> header.add("checkInventory","yello"))
                .build();
    }





}
