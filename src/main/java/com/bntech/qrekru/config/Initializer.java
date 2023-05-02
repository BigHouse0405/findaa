package com.bntech.qrekru.config;

import com.bntech.qrekru.data.model.Role;
import com.bntech.qrekru.data.model.User;
import com.bntech.qrekru.data.repository.RoleRepository;
import com.bntech.qrekru.data.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.bntech.qrekru.config.Const.ROLE_USER;

@Component
public class Initializer {
    private final RoleRepository roles;
    private final UserRepository users;

    @Autowired
    public Initializer(RoleRepository roles, UserRepository users) {
        this.roles = roles;
        this.users = users;
    }

    @PostConstruct
    public void init() {
        System.out.println("Initialize roles");
        Role r = new Role(ROLE_USER);
        roles.save(r);
        users.save(new User(
                null, "$2a$12$v10xlFdXkKDvdEHz06sJVekWfWYYPsUtnUNxUAwNwuKppdlioSkL.",
                "user", null, null, Set.of(r)));
    }
}
