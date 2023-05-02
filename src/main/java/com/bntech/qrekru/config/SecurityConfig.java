package com.bntech.qrekru.config;

import com.bntech.qrekru.api.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

import static com.bntech.qrekru.config.Const.*;

@Configuration
public class SecurityConfig {
    private final JwtRequestFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        String authEndpoint = api_VERSION + api_AUTHENTICATE;
        String swaggerEndpoint = api_SWAGGER_UI + "/**";
        String indexEndpoint = "/";
        String apiDocsEndpoint = "/v3/api-docs/**";

        return http.cors()
                .configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList(WILDCARD));
                    config.setAllowedMethods(Collections.singletonList(WILDCARD));
                    config.setAllowedHeaders(Collections.singletonList(WILDCARD));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                })
                .and().csrf()
                .disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .ignoringRequestMatchers(authEndpoint)
//                .and()
                .authorizeHttpRequests()
                .requestMatchers(authEndpoint, swaggerEndpoint, indexEndpoint, apiDocsEndpoint)
                .permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .headers().xssProtection()
                .and().contentSecurityPolicy("script-src 'self'")
                .and().and().build();
    }
}
