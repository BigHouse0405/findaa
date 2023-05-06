package com.bntech.bnauth.config;

import com.bntech.bnauth.api.JwtRequestFilter;
import com.bntech.bnauth.api.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

//  todo: make it work (handler)
@Configuration
@EnableWebSocketSecurity
public class SecurityConfigWs {
    private final JwtRequestFilter jwtFilter;
    private final CustomAuthenticationEntryPoint accessDeniedHandler;

    @Autowired
    public SecurityConfigWs(JwtRequestFilter jwtFilter, CustomAuthenticationEntryPoint accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages.simpDestMatchers("/user/queue/errors").permitAll()
                .simpDestMatchers("/admin/**").hasRole("ADMIN")
                .anyMessage().authenticated();
        return messages.build();
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> oauth2FilterRegistration() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/api/**");
        return registrationBean;
    }
}
