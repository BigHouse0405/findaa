package com.bntech.bnauth.service.impl;

import com.bntech.bnauth.data.model.Role;
import com.bntech.bnauth.data.repository.RoleRepository;
import com.bntech.bnauth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roles;

    @Autowired
    public RoleServiceImpl(RoleRepository roles) {
        this.roles = roles;
    }

    public Set<Role> upsertRoles(Set<Role> userRoles) {
        Set<String> roleNames = userRoles.stream().map(Role::getName).collect(Collectors.toSet());

        return roles.findRolesByNameIn(roleNames, PageRequest.of(0, Integer.MAX_VALUE)).stream()
                .filter(existingRole -> roleNames.contains(existingRole.getName()))
                .peek(roles::save)
                .collect(Collectors.toSet());
    }
}
