package com.awesomepizza.awesomepizzaapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private final HttpStatus status;
    private final String message;
}