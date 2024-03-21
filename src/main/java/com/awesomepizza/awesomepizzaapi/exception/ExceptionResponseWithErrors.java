package com.awesomepizza.awesomepizzaapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ExceptionResponseWithErrors {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ExceptionResponseWithErrors(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}