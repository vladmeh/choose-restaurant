package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.Menu;
import com.vladmeh.graduation.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 25.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@RepositoryRestResource(collectionResourceRel = "menu", path = "menu")
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @RestResource(path = "by-date")
    @Transactional(readOnly = true)
    List<Menu> findAllByDate(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @RestResource(path = "by-restaurant")
    @Transactional(readOnly = true)
    List<Menu> findAllByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Override
    @Secured("ROLE_ADMIN")
    Menu save(Menu entity);

    @Override
    @Secured("ROLE_ADMIN")
    void delete(Menu entity);
}
