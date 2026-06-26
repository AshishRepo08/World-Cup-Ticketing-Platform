package com.fifa.fifaWorldCup_OrderService.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderRequest(

        @NotBlank(message = "skuCode cannot be blank in OrderRequest Dto")
        String skuCode,

        @NotNull
        @Positive(message = "price should be a positive value")
        BigDecimal price,

        @NotNull
        @Positive(message = "quantity should be a positive value")
        Integer quantity,

        @NotNull(message = "userDetails should not be blank while placing orders")
        @Valid
        UserDetails userDetails) {

            public record UserDetails(
                    @NotBlank(message = "User's email cannot be blank")
                    @Email
                    String email,

                    @NotBlank(message = "User's first name cannot be blank")
                    String firstName,

                    @NotBlank(message = "User's last name cannot be blank")
                    String lastName) {}
}
