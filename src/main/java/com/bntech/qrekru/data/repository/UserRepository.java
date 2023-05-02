package com.bntech.qrekru.data.repository;


import com.bntech.qrekru.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
    User findByPwd(String pwd);
    User findByFullName(String name);
}
