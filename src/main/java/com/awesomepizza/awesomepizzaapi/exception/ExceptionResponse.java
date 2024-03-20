package com.awesomepizza.awesomepizzaapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ExceptionResponse {

    private final HttpStatus status;
    private final String message;
    private List<String> errors;

    public ExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ExceptionResponse(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}