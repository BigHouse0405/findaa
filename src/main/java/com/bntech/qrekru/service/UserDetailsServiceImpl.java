package com.bntech.qrekru.service;

import com.bntech.qrekru.data.model.User;
import com.bntech.qrekru.data.object.AppUserDetails;
import com.bntech.qrekru.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.bntech.qrekru.config.Const.ROLE_USER;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository users;

    @Autowired
    public UserDetailsServiceImpl(UserRepository users) {
        this.users = users;
    }

    @Override
    public AppUserDetails loadUserByUsername(final String username) {
        final User user = users.findByFullName(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        return new AppUserDetails(user.getId(), username, user.getPwd(),
                Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }
}
