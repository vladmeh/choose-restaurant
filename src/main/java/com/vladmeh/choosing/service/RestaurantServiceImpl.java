package com.vladmeh.choosing.service;

import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.repository.LunchRepository;
import com.vladmeh.choosing.to.LunchTo;
import com.vladmeh.choosing.to.RestaurantTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final LocalDate LOCAL_CURRENT_DATE = LocalDate.now();
    private final Logger log = LoggerFactory.getLogger(ChoiceService.class);
    private final LunchRepository lunchRepository;

    @Autowired
    public RestaurantServiceImpl(LunchRepository lunchRepository) {
        this.lunchRepository = lunchRepository;
    }

    @Override
    public RestaurantTo getWithLunchToday(Restaurant restaurant) {
        log.debug("Restaurant {} with lunch at {}", restaurant.getId(), LOCAL_CURRENT_DATE.toString());
        return new RestaurantTo(restaurant,
                lunchRepository.findAllByRestaurantAndDate(restaurant, LOCAL_CURRENT_DATE)
                        .stream().map(LunchTo::new).collect(Collectors.toList()));
    }
}
