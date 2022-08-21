package com.challenge.gladybackend.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AppNotFoundException extends AppException {

    public AppNotFoundException(String message) {
        super(message);
    }

    public AppNotFoundException(Throwable cause) {
        super(cause);
    }

    public AppNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }

    public AppNotFoundException(HttpStatus status, Throwable cause) {
        super(status, cause);
    }

    public AppNotFoundException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }

    @Override
    public AppNotFoundException trace(String message) {
        super.trace(message);
        return this;
    }

    @Override
    public AppNotFoundException trace(List<String> trace) {
        super.trace(trace);
        return this;
    }
}
