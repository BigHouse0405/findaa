package com.bntech.bnauth.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bntech.bnauth.config.Const.*;

@RestController
@RequestMapping(api_RESOURCES)
public class ResourceController {

    @Operation(summary = "Get error page css file")
    @GetMapping("/error.css")
    public Resource getErrorCss() {
        return new ClassPathResource(res_ERROR_CSS);
    }

    @Operation(summary = "Get pepe")
    @GetMapping("/pepe.png")
    public Resource getPepe() {
        System.out.println("Classpath Image load");
        return new ClassPathResource(res_PEPE);
    }
}
