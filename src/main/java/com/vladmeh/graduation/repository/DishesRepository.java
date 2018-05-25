package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 25.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@RepositoryRestResource(collectionResourceRel = "dishes", path = "dishes")
public interface DishesRepository extends JpaRepository<Dishes, Long> {
}
