package com.bntech.bnauth.api.controller;

import com.bntech.bnauth.api.handler.UserHandler;
import com.bntech.bnauth.data.object.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController(path = "/users")
public class UserController {
    private final UserHandler handler;

    @Autowired
    public UserController(UserHandler handler) {
        this.handler = handler;
    }

    @Operation(summary = "Update fullName for one user")
    @PatchMapping("/{id}/fullName")
    public ResponseEntity<?> updateFullName(@PathVariable("id") Long id, @RequestBody UserDto updates) {
        return handler.updateFullName(id, updates);
    }

    @Operation(summary = "Register new user")
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        return handler.createUser(user);
    }
}
