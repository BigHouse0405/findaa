package com.bntech.qrekru.data.repository;

import com.bntech.qrekru.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
    User findById(long id);
    User findByPwd(String pwd);
    Optional<User> findByFullName(String name);
    Boolean existsByFullName(String name);
}
