package com.challenge.gladybackend.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AppInvalidActionException extends AppException {

    public AppInvalidActionException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public AppInvalidActionException(Throwable cause) {
        super(HttpStatus.FORBIDDEN, cause);
    }

    public AppInvalidActionException(String message, Throwable cause) {
        super(message, HttpStatus.FORBIDDEN, cause);
    }

    public AppInvalidActionException(String message, HttpStatus status) {
        super(message, status);
    }

    public AppInvalidActionException(HttpStatus status, Throwable cause) {
        super(status, cause);
    }

    public AppInvalidActionException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }

    @Override
    public AppInvalidActionException trace(String message) {
        super.trace(message);
        return this;
    }

    @Override
    public AppInvalidActionException trace(List<String> trace) {
        super.trace(trace);
        return this;
    }
}
