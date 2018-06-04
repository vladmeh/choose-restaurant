package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Choice c WHERE c.user.id=:userId AND c.date=:date")
    Optional<Choice> getForUserAndDate(@Param("userId") Long userId, @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}
