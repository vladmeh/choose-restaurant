package com.vladmeh.choosing.web;

import com.vladmeh.choosing.model.Menu;
import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.service.ChoiceService;
import com.vladmeh.choosing.userdetails.UserPrincipal;
import com.vladmeh.choosing.util.ChoiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 04.06.2018.
 * @link choosing-topjava
 * @link https://docs.spring.io/spring-security/site/docs/current/reference/html/mvc.html#mvc-authentication-principal
 */

@RestController
@RequestMapping(value = "/api/choice", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
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
        ChoiceStatus choiceStatus = limit
                ? choiceService.saveAfterLimitTime(userPrincipal.getUser(), menu)
                : choiceService.save(userPrincipal.getUser(), menu);

        return new ResponseEntity<>(choiceStatus.getChoice().getRestaurant(), choiceStatus.isCreated() ? HttpStatus.CREATED : (limit ? HttpStatus.CONFLICT : HttpStatus.OK));
    }
}
