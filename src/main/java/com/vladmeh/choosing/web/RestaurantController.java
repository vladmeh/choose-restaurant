package com.vladmeh.choosing.web;

import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.service.RestaurantService;
import com.vladmeh.choosing.to.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 29.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */

@RestController
@RequestMapping(value = "/api/restaurants", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}/lunch")
    public ResponseEntity<RestaurantTo> getLunchToday(@PathVariable("id") Restaurant restaurant) {
        return new ResponseEntity<>(
                restaurantService.getWithLunchToday(restaurant), HttpStatus.OK);
    }
}
