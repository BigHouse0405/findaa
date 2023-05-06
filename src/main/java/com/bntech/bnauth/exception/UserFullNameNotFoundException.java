package com.bntech.bnauth.exception;

import org.springframework.security.core.AuthenticationException;

public class UserFullNameNotFoundException extends AuthenticationException {
    public UserFullNameNotFoundException(String msg) {
        super("User id: " + msg + " not found");
    }
}
