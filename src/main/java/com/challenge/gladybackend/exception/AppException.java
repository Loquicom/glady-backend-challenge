package com.challenge.gladybackend.exception;

import com.challenge.gladybackend.data.view.ErrorView;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class AppException extends Exception {

    protected HttpStatus status;

    protected List<String> trace = new ArrayList<>();

    public AppException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public AppException(Throwable cause) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public AppException(String message, Throwable cause) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public AppException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public AppException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public AppException trace(String message) {
        this.trace.add(message);
        return this;
    }

    public AppException trace(List<String> trace) {
        this.trace = trace;
        return this;
    }

    public List<String> getTrace() {
        return new ArrayList<>(this.trace);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public ErrorView toErrorView() {
        return new ErrorView(this.status.value(), this.getMessage(), this.getTrace());
    }

}
