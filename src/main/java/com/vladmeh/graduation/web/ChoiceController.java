package com.vladmeh.graduation.web;

import com.vladmeh.graduation.model.Menu;
import com.vladmeh.graduation.model.Restaurant;
import com.vladmeh.graduation.service.ChoiceService;
import com.vladmeh.graduation.userdetails.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link graduation-topjava
 * @link https://docs.spring.io/spring-security/site/docs/current/reference/html/mvc.html#mvc-authentication-principal
 */

@RestController
@RequestMapping(value = "/api/choice", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChoiceController {
    private static final LocalTime TIME_LIMIT = LocalTime.parse("11:00");

    private final ChoiceService choiceService;

    @Autowired
    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @GetMapping
    public ResponseEntity<Restaurant> current(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        return choiceService.getForUserAndDate(userPrincipal.getUser().getId(), LocalDate.now())
                .map(choice -> new ResponseEntity<>(choice.getRestaurant(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Restaurant> choice(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Menu menu) {
        LocalDate today = LocalDate.now();

        if (menu == null || !menu.getDate().equals(today)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean limit = LocalTime.now().isAfter(TIME_LIMIT);
        if (limit) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        choiceService.save(userPrincipal.getUser(), menu);
        return current(userPrincipal);
    }
}
