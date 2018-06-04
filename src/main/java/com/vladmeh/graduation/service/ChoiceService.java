package com.vladmeh.graduation.service;

import com.vladmeh.graduation.model.Choice;
import com.vladmeh.graduation.model.Menu;
import com.vladmeh.graduation.model.User;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link graduation-topjava
 */


public interface ChoiceService {
    Optional<Choice> getForUserAndDate(Long userId, LocalDate date);

    void save(User user, Menu menu);
}
