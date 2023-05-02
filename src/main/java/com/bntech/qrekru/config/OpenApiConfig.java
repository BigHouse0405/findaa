package com.bntech.qrekru.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Oauth2 Spring Server Example", version = "1.0",
        description = "User oauth2 authentication and authorization API",
        contact = @Contact(name = "Michał Cop", email = "michalcop@bntech.dev"))
)
@OpenAPI30
public class OpenApiConfig {
}
