package com.bntech.qrekru.service.impl;

import com.bntech.qrekru.api.ws.AuthenticationFailureEvent;
import com.bntech.qrekru.data.model.Role;
import com.bntech.qrekru.data.model.User;
import com.bntech.qrekru.data.object.AuthRequest;
import com.bntech.qrekru.data.object.AuthResponse;
import com.bntech.qrekru.data.object.UserDto;
import com.bntech.qrekru.data.repository.UserRepository;
import com.bntech.qrekru.exception.InvalidCredentialsException;
import com.bntech.qrekru.exception.UserIdNotFoundException;
import com.bntech.qrekru.service.RoleService;
import com.bntech.qrekru.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository users;
    private final RoleService roles;
    private final AuthenticationManager auth;
    private final UserDetailsService securityUsers;
    private final JwtServiceImpl jwt;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public AuthResponse authenticate(final AuthRequest authenticationRequest){
        String username = authenticationRequest.getLogin();

        try {
            auth.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            System.out.println("Bad creds");
            messagingTemplate.convertAndSend("/topic/errors", new AuthenticationFailureEvent("Invalid credentials provided for user: " + username, Instant.now()));
            throw new InvalidCredentialsException(username);
        }

        final UserDetails userDetails = securityUsers.loadUserByUsername(username);
        final AuthResponse authenticationResponse = new AuthResponse();
        authenticationResponse.setAccessToken(jwt.keygen(userDetails));
        return authenticationResponse;
    }

    @Autowired
    public UserServiceImpl(UserRepository users, RoleServiceImpl roles, AuthenticationManager auth, UserDetailsService securityUsers, JwtServiceImpl jwt, SimpMessagingTemplate messagingTemplate) {
        this.users = users;
        this.roles = roles;
        this.auth = auth;
        this.securityUsers = securityUsers;
        this.jwt = jwt;
        this.messagingTemplate = messagingTemplate;
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
