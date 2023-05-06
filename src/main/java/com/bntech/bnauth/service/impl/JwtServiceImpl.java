package com.bntech.bnauth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bntech.bnauth.service.JwtService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

import static com.bntech.bnauth.config.Const.TOKEN_DURATION;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Getter
    private final Algorithm hmac512;
    @Getter
    private final JWTVerifier check;

    public JwtServiceImpl(ResourceLoader resourceLoader) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:certs/jwt-secret.pem");
        String secret = new String(resource.getInputStream().readAllBytes());
        this.hmac512 = Algorithm.HMAC512(secret);
        this.check = JWT.require(this.hmac512).build();
    }

    @Override
    public String keygen(final UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(TOKEN_DURATION.toMillis()))
                .sign(this.hmac512);
    }

    @Override
    public String validate(final String token) {
        try {
            return check.verify(token).getSubject();
        } catch (final JWTVerificationException ve) {
            log.info(ve.getMessage());
            throw new JWTVerificationException("Invalid token");
        }
    }
}
