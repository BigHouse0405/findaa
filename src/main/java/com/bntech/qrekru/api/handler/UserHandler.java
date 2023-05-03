package com.bntech.qrekru.api.handler;

import com.bntech.qrekru.data.model.Role;
import com.bntech.qrekru.data.object.UserDto;
import com.bntech.qrekru.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.bntech.qrekru.api.HttpUtil.ok;
import static com.bntech.qrekru.api.HttpUtil.badRequest;

@Component
public class UserHandler {
    private final UserService users;

    @Autowired
    public UserHandler(UserService users) {
        this.users = users;
    }

    public ResponseEntity<?> updateFullName(Long id, UserDto updates) {
        ResponseEntity<?> verificationResult = verifier(updates.getFullName(), "Incorrect data provided to update user full name");

        if (verificationResult != null) {
            return verificationResult;
        }

        return ok(users.updateUserFullName(updates.getFullName(), id));
    }

    public ResponseEntity<?> createUser(UserDto user) {
        ResponseEntity<?> verificationResult = verifier(user.getFullName(), "Incorrect data provided to create user");
        if (verificationResult != null) {
            return verificationResult;
        }

        Set<Role> dtoRoles = new HashSet<>();

        if (user.getRoles() != null) {
            for (String role : user.getRoles()) {
                dtoRoles.add(new Role(role));
            }
        }

        if (!dtoRoles.isEmpty()) {
            if (dtoRoles.size() == 1) {
                Role r = new Role("role_user");
                dtoRoles.removeIf(role -> !role.getName().equals(""));
                dtoRoles.add(r);
            }
        } else {
            dtoRoles.add(new Role("role_user"));
        }

        return ok(users.save(user, dtoRoles));
    }

    private ResponseEntity<?> verifier(String str, String errorMessage) {
        if (str == null || str.trim().equals("")) {
            return badRequest(errorMessage);
        }
        return null;
    }
}
