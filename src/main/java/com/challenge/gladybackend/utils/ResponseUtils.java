package com.challenge.gladybackend.utils;

import com.challenge.gladybackend.data.view.ErrorView;
import com.challenge.gladybackend.exception.AppException;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity<ErrorView> response(AppException result) {
        return ResponseEntity.status(result.getStatus()).body(result.toErrorView());
    }

    public static <T> ResponseEntity<T> response(T result) {
        return ResponseEntity.ok().body(result);
    }

    public static ResponseEntity<ErrorView> error(Exception exception) {
        return error(exception, false);
    }

    public static ResponseEntity<ErrorView> error(Exception exception, boolean hide) {
        AppException appException;
        if (!hide && exception.getMessage() != null) {
            appException = new AppException(exception.getMessage(), exception.getCause());
        } else {
            appException = new AppException("Unknown error");
        }
        return response(appException);
    }

}
