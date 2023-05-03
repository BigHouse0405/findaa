package com.bntech.qrekru.service.impl;

import com.bntech.qrekru.data.model.Role;
import com.bntech.qrekru.data.model.User;
import com.bntech.qrekru.data.object.UserDto;
import com.bntech.qrekru.data.repository.UserRepository;
import com.bntech.qrekru.exception.UserIdNotFoundException;
import com.bntech.qrekru.service.RoleService;
import com.bntech.qrekru.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository users;
    private final RoleService roles;

    @Autowired
    public UserServiceImpl(UserRepository users, RoleServiceImpl roles) {
        this.users = users;
        this.roles = roles;
    }

    @Override
    public User getById(Long id) {
        return users.findById(id).orElseThrow(() -> new UserIdNotFoundException(String.valueOf(id)));
    }

    @Override
    public User updateUserFullName(String fullName, Long id) {
        User u = users.findById(id).orElseThrow(() -> new UserIdNotFoundException(String.valueOf(id)));
        u.setFullName(fullName);
        return users.save(u);
    }

    @Override
    public User save(User user) {
        return users.save(user);
    }

    @Override
    public User save(UserDto user, Set<Role> dtoRoles) {
        User u = new User(user.getPwd(), user.getFullName(), Set.of(roles.upsertRoles(dtoRoles).toArray(new Role[0])));
        return users.save(u);
    }
}
