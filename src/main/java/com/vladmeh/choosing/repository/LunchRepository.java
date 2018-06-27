package com.vladmeh.choosing.repository;

import com.vladmeh.choosing.model.Lunch;
import com.vladmeh.choosing.model.Restaurant;
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
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 25.06.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 */

@RepositoryRestResource(collectionResourceRel = "lunch", path = "lunch")
public interface LunchRepository extends JpaRepository<Lunch, Long> {

    @RestResource(path = "by-date")
    @Transactional(readOnly = true)
    List<Lunch> findAllByDate(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @RestResource(path = "by-restaurant")
    @Transactional(readOnly = true)
    List<Lunch> findAllByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Override
    @Secured("ROLE_ADMIN")
    Lunch save(Lunch entity);

    @Override
    @Secured("ROLE_ADMIN")
    void delete(Lunch entity);

}
