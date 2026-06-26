package com.fifa.fifaWorldCup_OrderService.customExceptions;

public class OutOfStockException extends RuntimeException {

    private String message;

    public OutOfStockException(String message) {
        this.message = message;
    }
}
