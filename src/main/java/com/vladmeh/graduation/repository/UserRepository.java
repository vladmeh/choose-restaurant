package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @autor mvl on 21.05.2018.
 */

@RepositoryRestResource(collectionResourceRel = "user", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByName(@Param("name") String name);
}
