package com.bntech.qrekru.service.impl;

import com.bntech.qrekru.data.model.Role;
import com.bntech.qrekru.data.repository.RoleRepository;
import com.bntech.qrekru.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roles;

    @Autowired
    public RoleServiceImpl(RoleRepository roles) {
        this.roles = roles;
    }

    public Set<Role> upsertRoles(Set<Role> userRoles) {
        Set<String> roleNames = new HashSet<>();

        for (Role role : userRoles) {
            roleNames.add(role.getName());
        }

        Set<Role> newRoles = roles.findRolesByNameIn(roleNames).orElse(new HashSet<>());
        newRoles.stream()
                .filter(role -> !roleNames.contains(role.getName()))
                .forEach(newRoles::remove);

        roles.saveAll(newRoles);

        return newRoles;
    }
}
