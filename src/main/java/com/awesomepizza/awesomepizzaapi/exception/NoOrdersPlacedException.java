package com.awesomepizza.awesomepizzaapi.exception;

public class NoOrdersPlacedException extends RuntimeException {
    public NoOrdersPlacedException(String message) {
        super(message);
    }
}