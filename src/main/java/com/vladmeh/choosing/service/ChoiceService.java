package com.vladmeh.choosing.service;

import com.vladmeh.choosing.model.Choice;
import com.vladmeh.choosing.model.Menu;
import com.vladmeh.choosing.model.User;
import com.vladmeh.choosing.util.ChoiceStatus;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link choosing-topjava
 */


public interface ChoiceService {
    Optional<Choice> getForUserAndDate(Long userId, LocalDate date);

    ChoiceStatus save(User user, Menu menu);

    ChoiceStatus saveAfterLimitTime(User user, Menu menu);
}
