package com.vladmeh.choosing.web;

import com.vladmeh.choosing.model.Lunch;
import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.service.ChoiceService;
import com.vladmeh.choosing.to.LunchTo;
import com.vladmeh.choosing.to.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 29.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */

@RestController
@RequestMapping(value = "/api/restaurants", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class RestaurantController {

    private final ChoiceService choiceService;

    @Autowired
    public RestaurantController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @GetMapping("/{id}/lunch")
    public ResponseEntity<RestaurantTo> getLunchToday(@PathVariable("id") Restaurant restaurant){
        List<Lunch> lunchList = choiceService.getForRestaurantAndDate(
                restaurant, LocalDate.now());

        List<LunchTo> lunchToList = new ArrayList<>();

        for (Lunch lunch: lunchList){
            lunchToList.add(new LunchTo(lunch));
        }

        return new ResponseEntity<>(
                new RestaurantTo(restaurant, lunchToList), HttpStatus.OK);
    }
}
