package com.bntech.bnauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").
                setAllowedOrigins("https://bnauth_bnauth_1:8443",
                        "https://bnauth_bnauth_1:",
                        "http://bnauth_bnauth_1:8443",
                        "http://bnauth_bnauth_1:",
                        "https://bnauth_ws-client_1:3000",
                        "https://bnauth_ws-client_1:",
                        "http://bnauth_ws-client_1:3000",
                        "http://bnauth_ws-client_1:",
                        "https://localhost",
                        "http://localhost",
                        "https://localhost:3000",
                        "http://localhost:3000").withSockJS();
    }
}
