package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.Dishes;
import com.vladmeh.graduation.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

@RepositoryRestResource(collectionResourceRel = "dishes", path = "dishes")
public interface DishesRepository extends JpaRepository<Dishes, Long> {

    @RestResource(path = "by-menu")
    @Transactional(readOnly = true)
    List<Dishes> findAllByMenu(@Param("menu") Menu menu);

    @RestResource(path = "by-date")
    @Transactional(readOnly = true)
    @Query("SELECT d FROM Dishes d WHERE d.menu.date=:date")
    List<Dishes> findByDate(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @Override
    @Secured("ROLE_ADMIN")
    Dishes save(Dishes entity);

    @Override
    @Secured("ROLE_ADMIN")
    void delete(Dishes entity);
}
