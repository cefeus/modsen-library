package org.authservice.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.authservice.model.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NullPointerException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
    })
    public ErrorResponse handleOtherException(RuntimeException e) {
        val exceptionId = UUID.randomUUID().toString();

        return ErrorResponse.builder()
                .id(exceptionId)
                .message("An error occurred")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        val exceptionId = UUID.randomUUID().toString();
        val message = e.getMessage();

        return ErrorResponse.builder()
                .id(exceptionId)
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        val exceptionId = UUID.randomUUID().toString();
        val message = e.getMessage();

        return ErrorResponse.builder()
                .id(exceptionId)
                .message(message)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(EntityExistsException.class)
    public ErrorResponse handleEntityAlreadyExistsException(RuntimeException e) {
        val exceptionId = UUID.randomUUID().toString();
        val message = e.getMessage();

        return ErrorResponse.builder()
                .id(exceptionId)
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
