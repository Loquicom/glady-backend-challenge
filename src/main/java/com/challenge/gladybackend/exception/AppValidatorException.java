package com.challenge.gladybackend.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AppValidatorException extends AppException {

    public AppValidatorException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public AppValidatorException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, cause);
    }

    public AppValidatorException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }

    public AppValidatorException(String message, HttpStatus status) {
        super(message, status);
    }

    public AppValidatorException(HttpStatus status, Throwable cause) {
        super(status, cause);
    }

    public AppValidatorException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }

    @Override
    public AppValidatorException trace(String message) {
        super.trace(message);
        return this;
    }

    @Override
    public AppValidatorException trace(List<String> trace) {
        super.trace(trace);
        return this;
    }

}
