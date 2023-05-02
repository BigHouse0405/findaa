package com.bntech.qrekru.data.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static com.bntech.qrekru.config.Const.*;

@Entity
@Table(name = USERS_TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pwd;
    @Column(name = USERS_FULL_NAME)
    private String fullName;

    @Column(name = USERS_CREATED_AT)
    @Temporal(TemporalType.TIMESTAMP)
    @Nullable
    private Instant createdAt;

    @Column(name = USERS_UPDATED_AT)
    @Temporal(TemporalType.TIMESTAMP)
    @Nullable
    private Instant updatedAt;

    @RestResource(path = "roles", rel="role")
    @Nullable
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = USER_ROLE_TABLE,
            joinColumns = @JoinColumn(name = USER_ROLE_USER_ID),
            inverseJoinColumns = @JoinColumn(name = USER_ROLE_ROLE_ID))
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        createdAt = Instant.now();
    }
}
