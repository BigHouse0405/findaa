package com.bntech.bnauth.api.controller;

import com.bntech.bnauth.data.object.AuthRequest;
import com.bntech.bnauth.data.object.AuthResponse;
import com.bntech.bnauth.service.UserService;
import com.bntech.bnauth.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import static com.bntech.bnauth.config.Const.*;

@RestController
@RequestMapping(api_VERSION)
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get CSRF token")
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @Operation(summary = "OAuth2 Authentication endpoint")
    @PostMapping(api_AUTHENTICATE)
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid final AuthRequest authenticationRequest) {
        HttpHeaders headers = new HttpHeaders();
        AuthResponse authResponse = userService.authenticate(authenticationRequest);
        return ResponseEntity.ok().headers(headers).body(authResponse);
    }
}
