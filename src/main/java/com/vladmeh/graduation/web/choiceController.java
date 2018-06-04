package com.vladmeh.graduation.web;

import com.vladmeh.graduation.model.Restaurant;
import com.vladmeh.graduation.model.User;
import com.vladmeh.graduation.service.ChoiceService;
import com.vladmeh.graduation.userdetails.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link graduation-topjava
 */

@RestController
@RequestMapping(value = "/api/choice", produces = MediaType.APPLICATION_JSON_VALUE)
public class choiceController {

    private final ChoiceService choiceService;

    @Autowired
    public choiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @GetMapping
    public ResponseEntity<Restaurant> current() {
        return choiceService.getForUserAndDate(UserPrincipal.id(), LocalDate.now())
                .map(choice -> new ResponseEntity<>(choice.getRestaurant(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Restaurant> choice(@PathVariable("id") Restaurant restaurant) {
        choiceService.save(UserPrincipal.user(), restaurant);
        return current();
    }
}
