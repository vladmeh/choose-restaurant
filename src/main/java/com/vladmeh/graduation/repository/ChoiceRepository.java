package com.vladmeh.graduation.repository;

import com.vladmeh.graduation.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Choice c WHERE c.user.id=:userId AND c.date=:date")
    List<Choice> getForUserAndDate(@Param("userId") int userId, @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}
