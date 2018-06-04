package com.vladmeh.graduation.service;

import com.vladmeh.graduation.model.Choice;
import com.vladmeh.graduation.model.Menu;
import com.vladmeh.graduation.model.User;
import com.vladmeh.graduation.repository.ChoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link graduation-topjava
 */

@Service
public class ChoiceServiceImpl implements ChoiceService {

    private final Logger log = LoggerFactory.getLogger(ChoiceService.class);

    private final ChoiceRepository choiceRepository;

    @Autowired
    public ChoiceServiceImpl(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Optional<Choice> getForUserAndDate(Long userId, LocalDate date) {
        log.debug("Authorized {}", userId);
        return choiceRepository.getForUserAndDate(userId, date);
    }

    @Override
    @Transactional
    public Choice save(User user, Menu menu) {
        LocalDate date = menu.getDate();
        Choice choice = choiceRepository.getForUserAndDate(user.getId(), date)
                .map(c -> {
                    c.setRestaurant(menu.getRestaurant()); //update
                    return c;
                })
                .orElseGet(() -> new Choice(
                        user, menu.getRestaurant(), date)); //created

        choiceRepository.save(choice);

        return choice;
    }

}
