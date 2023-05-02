package com.bntech.qrekru.data.repository;


import com.bntech.qrekru.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>, JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
