package com.bntech.bnauth.data.repository;

import com.bntech.bnauth.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

//todo: when adding new user with same name returns 200 despite error.
@RepositoryRestResource(collectionResourceRel = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    Optional<User> findByFullName(@Param("fullName") String fullName);

    Boolean existsByFullName(String name);

    @Override
    Page<User> findAll(Pageable pageable);
}
