package com.bntech.qrekru.api;

import com.bntech.qrekru.exception.UserIdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class Adviser {

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = BAD_REQUEST)
    protected ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return HttpUtil.badRequest(ex.getMessage());
    }

    @ExceptionHandler(value = UserIdNotFoundException.class)
    @ResponseStatus(value = BAD_REQUEST)
    protected ResponseEntity<?> handleUserIdNotFound(HttpMessageNotReadableException ex) {
        return HttpUtil.badRequest(ex.getMessage());
    }
}
