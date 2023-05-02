package com.bntech.qrekru.api;

import com.bntech.qrekru.data.object.AuthRequest;
import com.bntech.qrekru.data.object.AuthResponse;
import com.bntech.qrekru.service.JwtTokenService;
import com.bntech.qrekru.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static com.bntech.qrekru.config.Const.api_AUTHENTICATE;
import static com.bntech.qrekru.config.Const.api_VERSION;

@RestController()
@RequestMapping(api_VERSION)
public class AuthController {
    private final AuthenticationManager auth;
    private final UserDetailsService users;
    private final JwtTokenService jwt;

    @Autowired
    public AuthController(AuthenticationManager auth, UserDetailsServiceImpl users, JwtTokenService jwt) {
        this.auth = auth;
        this.users = users;
        this.jwt = jwt;
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @PostMapping(api_AUTHENTICATE)
    public AuthResponse authenticate(@RequestBody @Valid final AuthRequest authenticationRequest) {
        try {
            auth.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = users.loadUserByUsername(authenticationRequest.getLogin());
        final AuthResponse authenticationResponse = new AuthResponse();
        authenticationResponse.setAccessToken(jwt.keygen(userDetails));
        return authenticationResponse;
    }
}
