package com.awesomepizza.awesomepizzaapi.exception;

public class OrderQueueException extends RuntimeException {
    public OrderQueueException(String message) {
        super(message);
    }
}