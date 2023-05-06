package com.bntech.bnauth.config;

import com.bntech.bnauth.data.model.Role;
import com.bntech.bnauth.data.model.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
@OpenAPIDefinition(info = @Info(title = "Oauth2 Spring Server Example", version = "1.0",
        description = "User oauth2 authentication and authorization API",
        contact = @Contact(name = "Michał Cop", email = "michalcop@bntech.dev"))
)
@OpenAPI30
public class AppConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class, Role.class);
    }
}
