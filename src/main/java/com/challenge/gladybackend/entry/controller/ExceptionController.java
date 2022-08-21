package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.view.ErrorView;
import com.challenge.gladybackend.exception.AppException;
import com.challenge.gladybackend.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @Value("${app.security.hide-internal-error}")
    private boolean hide;

    /**
     * Handle application exceptions to send an error response for the user
     *
     * @param request   HTTP request
     * @param exception Exception catch
     * @return Error view
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorView> appExceptionHandler(HttpServletRequest request, AppException exception) {
        log.info("App exception: {}", exception.getMessage());
        List<String> trace = exception.getTrace();
        if (trace.size() > 0) {
            log.info("App exception trace : {}", trace);
        }
        return ResponseUtils.response(exception);
    }

    /**
     * Handle exception when request can't be read
     *
     * @param request   HTTP request
     * @param exception Exception catch
     * @return Error view
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorView> httpMessageNotReadableExceptionHandler(HttpServletRequest request,
        HttpMessageNotReadableException exception) {
        log.info("HTTP message not readable exception", exception);
        return ResponseUtils.response(new AppException("Unable to read request", HttpStatus.BAD_REQUEST));
    }

    /**
     * Handle all exceptions they are not application exceptions (mainly unattended exception) to send an error response for the user
     *
     * @param request   HTTP request
     * @param exception Exception catch
     * @return Error view
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorView> exceptionHandler(HttpServletRequest request, Exception exception) {
        log.warn("Unattended exception,", exception);
        return ResponseUtils.error(exception, hide);
    }

}
