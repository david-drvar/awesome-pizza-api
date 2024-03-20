package com.awesomepizza.awesomepizzaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest ignoredRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderQueueException.class)
    public ResponseEntity<?> handleOrderQueueException(OrderQueueException ex, WebRequest ignoredRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoOrdersPlacedException.class)
    public ResponseEntity<?> handleNoOrdersPlacedException(NoOrdersPlacedException ex, WebRequest ignoredRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.OK, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<?> handleUsernameExistsException(UsernameExistsException ex, WebRequest ignoredRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.OK, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest ignoredRequest) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, "Validation error", errorMessages);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest ignoredRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGlobalRuntimeException(RuntimeException ex, WebRequest ignoredRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
