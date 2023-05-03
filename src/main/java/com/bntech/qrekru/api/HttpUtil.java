package com.bntech.qrekru.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class HttpUtil {

    public static ResponseEntity<?> generic(Object body, int status) {
        return ResponseEntity.status(status).body(body);
    }

    public static ResponseEntity<?> badRequest(String message) {
        return generic(Map.of("message", message), HttpStatus.BAD_REQUEST.value());
    }

    public static ResponseEntity<?> notFound(String message) {
        return generic(Map.of("message", message), HttpStatus.NOT_FOUND.value());
    }

    public static ResponseEntity<?> ok(Object body) {
        return generic(body, HttpStatus.OK.value());
    }
}
