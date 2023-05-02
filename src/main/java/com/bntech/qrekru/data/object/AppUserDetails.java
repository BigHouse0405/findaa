package com.bntech.qrekru.data.object;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {
    public final Long id;

    public AppUserDetails(final Long id, final String username, final String hash, final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.id = id;
    }
}
