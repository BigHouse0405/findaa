package com.bntech.bnauth.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.bntech.bnauth.config.Const.api_403;
import static com.bntech.bnauth.config.Const.api_ERROR;

@Controller
@RequestMapping(api_ERROR)
public class ExceptionController {

    @Operation(summary = "Get 403 custom error page")
    @GetMapping(api_403)
    public String error403() {
        return api_ERROR + api_403;
    }
}
