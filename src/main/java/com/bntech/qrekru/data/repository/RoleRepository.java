package com.bntech.qrekru.data.repository;


import com.bntech.qrekru.data.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Page<Role> findRolesByNameIn(Set<String> roles, Pageable pageable);
}

