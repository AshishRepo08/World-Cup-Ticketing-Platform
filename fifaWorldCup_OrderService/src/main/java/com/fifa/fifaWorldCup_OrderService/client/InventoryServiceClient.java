package com.fifa.fifaWorldCup_OrderService.client;

import com.fifa.fifaWorldCup_OrderService.dto.InventoryReservationRequest;
import com.fifa.fifaWorldCup_OrderService.dto.InventoryReservationResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceClient {
    private final RestClient restClient;


    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer ticketsNeeded) {
        log.info("Rest Client Method: Requesting quantity for product : "+skuCode);

        /**
         * Concat Version
        String requestParameters = "/inventory?skuCode=" +skuCode +"&ticketsNeeded="+ticketsNeeded;
        boolean result = restClient.get().uri(requestParameters).retrieve().body(new ParameterizedTypeReference<Boolean>() {});
        **/
        System.out.println("In orderService method : "+ticketsNeeded);
        return Boolean.TRUE.equals(restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/inventory")
                        .queryParam("skuCode", skuCode)
                        .queryParam("ticketsNeeded", ticketsNeeded)
                        .build())
                .retrieve()
                .body(Boolean.class));
    }


    public InventoryReservationResponse reserveTickets(String skuCode, Integer ticketsNeeded) {
        log.info("Calling Inventory Service to reserve tickets. skuCode={}, quantity={}", skuCode, ticketsNeeded);

        InventoryReservationRequest request = new InventoryReservationRequest(skuCode, ticketsNeeded);

        return restClient.post()
                .uri("/inventory/reserve")
                .body(request)
                .retrieve()
                .body(InventoryReservationResponse.class);
    }
}
