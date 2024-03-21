package com.awesomepizza.awesomepizzaapi.exception;

public class OrderModificationException extends RuntimeException {
    public OrderModificationException(String message) {
        super(message);
    }
}