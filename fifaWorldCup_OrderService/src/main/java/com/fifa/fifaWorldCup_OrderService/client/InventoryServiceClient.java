package com.fifa.fifaWorldCup_OrderService.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
public class InventoryServiceClient {
    private final RestClient restClient;


    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        System.out.println("Rest Client Method: Requesting quantity for product : "+skuCode);

        boolean result = restClient.get().uri("/inventory?skuCode=Spain vs Saudi Arabia&ticketsNeeded=90").retrieve().body(new ParameterizedTypeReference<Boolean>() {});
        return result;
    }
}
