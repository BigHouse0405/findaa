package com.bntech.qrekru.service.impl;

import com.bntech.qrekru.data.model.Role;
import com.bntech.qrekru.data.repository.RoleRepository;
import com.bntech.qrekru.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
