package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@RepositoryRestResource(collectionResourceRel = "restaurant", path = "restaurants")
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @RestResource(path = "by-name")
    @Transactional(readOnly = true)
    Restaurant findByName(@Param("name") String name);
}
