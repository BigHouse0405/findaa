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
                setAllowedOrigins("https://bnauth-bnauth-1:8443",
                        "https://bnauth-bnauth-1",
                        "http://bnauth-bnauth-1:8443",
                        "http://bnauth-bnauth-1",
                        "https://bnauth-ws-client-1:3000",
                        "https://bnauth-ws-clien-1",
                        "http://bnauth-ws-client-1:3000",
                        "http://bnauth-ws-client-1",
                        "https://localhost",
                        "http://localhost",
                        "https://localhost:3000",
                        "http://localhost:3000").withSockJS();
    }
}
