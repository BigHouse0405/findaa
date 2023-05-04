package com.bntech.qrekru.config;

import com.bntech.qrekru.api.JwtRequestFilter;
import com.bntech.qrekru.api.handler.CustomAuthenticationEntryPoint;
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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static com.bntech.qrekru.config.Const.*;

@Configuration
public class SecurityConfig {
    private final JwtRequestFilter jwtFilter;
    private final CustomAuthenticationEntryPoint accessDeniedHandler;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtFilter, CustomAuthenticationEntryPoint accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
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
        String apiErrorEndpoint = api_ERROR + api_403;
        String swaggerEndpoint = api_SWAGGER_UI + "/**";
        String resourcesEndpoint = api_RESOURCES + "/**";
        String indexEndpoint = "/";
        String apiDocsEndpoint = "/v3/api-docs/**";
        String wsEndpoint = "/ws/**";

        return http.cors()
                .configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(
                            "http://localhost:3000",
                            "http://localhost",
                            "https://localhost",
                            "https://localhost:3000"));
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
                .requestMatchers(
                        authEndpoint,
                        swaggerEndpoint,
                        indexEndpoint,
                        apiDocsEndpoint,
                        apiErrorEndpoint,
                        resourcesEndpoint,
                        wsEndpoint)
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
                .and().and().exceptionHandling().authenticationEntryPoint(accessDeniedHandler).and().build();
    }

}
