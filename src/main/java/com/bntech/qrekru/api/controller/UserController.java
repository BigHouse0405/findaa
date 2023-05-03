package com.bntech.qrekru.api.controller;

import com.bntech.qrekru.api.handler.UserHandler;
import com.bntech.qrekru.data.object.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bntech.qrekru.config.Const.api_USERS;

@RepositoryRestController(path = api_USERS)
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
