package com.bntech.qrekru.exception;

import org.springframework.security.core.AuthenticationException;

public class UserIdNotFoundException extends AuthenticationException {
    public UserIdNotFoundException(String msg) {
        super("User id: " + msg + " not found");
    }
}
