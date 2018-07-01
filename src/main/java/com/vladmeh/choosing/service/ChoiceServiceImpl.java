package com.vladmeh.choosing.service;

import com.vladmeh.choosing.exceptions.DataNotFoundException;
import com.vladmeh.choosing.exceptions.ResourceNotFoundException;
import com.vladmeh.choosing.exceptions.RestaurantNotFoundException;
import com.vladmeh.choosing.exceptions.ValidationLimitException;
import com.vladmeh.choosing.model.Choice;
import com.vladmeh.choosing.model.Lunch;
import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.model.User;
import com.vladmeh.choosing.repository.ChoiceRepository;
import com.vladmeh.choosing.repository.LunchRepository;
import com.vladmeh.choosing.util.ChoiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link choosing-topjava
 */

@Service
public class ChoiceServiceImpl implements ChoiceService {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalTime TIME_LIMIT = LocalTime.parse("11:00");
    private final Logger log = LoggerFactory.getLogger(ChoiceService.class);
    private final ChoiceRepository choiceRepository;
    private final LunchRepository lunchRepository;

    @Autowired
    public ChoiceServiceImpl(ChoiceRepository choiceRepository, LunchRepository lunchRepository) {
        this.choiceRepository = choiceRepository;
        this.lunchRepository = lunchRepository;
    }

    @Override
    public Optional<Choice> getForUserAndDate(Long userId, LocalDate date) {
        log.debug("Authorized {}", userId);
        return choiceRepository.getForUserAndDate(userId, date);
    }

    @Override
    public List<Lunch> getForRestaurantAndDate(Restaurant restaurant, LocalDate date) {
        return lunchRepository.findAllByRestaurantAndDate(restaurant, date);
    }

    @Override
    @Transactional
    public ChoiceStatus save(User user, Restaurant restaurant) {
        ChoiceStatus choiceStatus = choiceRepository.getForUserAndDate(user.getId(), TODAY)
                .map(c -> {
                    c.setRestaurant(restaurant); //create
                    return new ChoiceStatus(c, false);
                })
                .orElseGet(() -> new ChoiceStatus(
                        new Choice(user, restaurant, TODAY), true)); //update

        choiceRepository.save(choiceStatus.getChoice());

        return choiceStatus;
    }

    @Override
    @Transactional
    public ChoiceStatus saveAfterLimitTime(User user, Restaurant restaurant) {
        return choiceRepository.getForUserAndDate(user.getId(), TODAY)
                .map(c -> new ChoiceStatus(c, false))
                .orElseGet(() -> new ChoiceStatus(choiceRepository.save(new Choice(user, restaurant, TODAY)), true));
    }

    @Override
    public Restaurant getCurrent(User user) throws RestaurantNotFoundException {
        return getForUserAndDate(user.getId(), TODAY).map(Choice::getRestaurant).orElseThrow(() -> new RestaurantNotFoundException("The restaurant was not chosen"));
    }

    @Override
    public Restaurant setChoice(User user, Restaurant restaurant) {

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        if (getForRestaurantAndDate(restaurant, TODAY).isEmpty()) {
            throw new DataNotFoundException("The restaurant does not have lunch for choice");
        }

        boolean limit = LocalTime.now().isAfter(TIME_LIMIT);
        ChoiceStatus choiceStatus = limit
                ? saveAfterLimitTime(user, restaurant)
                : save(user, restaurant);

        if (!choiceStatus.isCreated() && limit) throw new ValidationLimitException("Choices time expired");

        return choiceStatus.getChoice().getRestaurant();
    }
}
