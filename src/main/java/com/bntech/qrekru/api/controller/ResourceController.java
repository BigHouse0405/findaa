package com.bntech.qrekru.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bntech.qrekru.config.Const.*;

@RestController
@RequestMapping(api_RESOURCES)
public class ResourceController {

    @Operation(summary = "Get error page css file")
    @GetMapping(api_CSS_ERROR)
    public Resource getErrorCss() {
        return new ClassPathResource("static/css/error.css");
    }
    @Operation(summary = "Get pepe")
    @GetMapping(api_PEPE)
    public Resource getPepe(){
        System.out.println("Classpath Image load");
        return new ClassPathResource("static/images/pepe.png");
    }
}
