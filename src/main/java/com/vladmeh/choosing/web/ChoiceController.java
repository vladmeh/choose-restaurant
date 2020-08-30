package com.vladmeh.choosing.web;

import com.vladmeh.choosing.exceptions.RestaurantNotFoundException;
import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.service.ChoiceService;
import com.vladmeh.choosing.userdetails.UserPrincipal;
import com.vladmeh.choosing.util.ChoiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link choosing-topjava
 * @link https://docs.spring.io/spring-security/site/docs/current/reference/html/mvc.html#mvc-authentication-principal
 */

@RestController
@RequestMapping(value = "/api/choice", produces = MediaTypes.HAL_JSON_VALUE)
public class ChoiceController {
    private final ChoiceService choiceService;

    @Autowired
    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @GetMapping
    public ResponseEntity<Restaurant> current(@AuthenticationPrincipal UserPrincipal userPrincipal) throws RestaurantNotFoundException {
        return new ResponseEntity<>(choiceService.getCurrent(userPrincipal.getUser()), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Restaurant> choice(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Restaurant restaurant) {
        ChoiceStatus choiceStatus = choiceService.choiceStatus(userPrincipal.getUser(), restaurant);
        return new ResponseEntity<>(choiceStatus.getChoice().getRestaurant(), choiceStatus.isCreated() ? HttpStatus.CREATED : HttpStatus.OK);
    }
}
