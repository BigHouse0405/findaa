package com.bntech.qrekru.api;

import com.bntech.qrekru.exception.InvalidCredentialsException;
import com.bntech.qrekru.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.bntech.qrekru.api.HttpUtil.badRequest;
import static com.bntech.qrekru.api.HttpUtil.generic;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class Adviser {

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = BAD_REQUEST)
    protected ResponseEntity<?> handleHttpMessageNotReadable(Exception ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(value = UserIdNotFoundException.class)
    @ResponseStatus(value = BAD_REQUEST)
    protected ResponseEntity<?> handleUserIdNotFound(Exception ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    @ResponseStatus(value = BAD_REQUEST)
    protected ResponseEntity<?> handleResponseStatusException(Exception ex) {
        return generic(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
    }
}
