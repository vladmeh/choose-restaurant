package com.vladmeh.choosing.testdata;

import com.vladmeh.choosing.model.Lunch;
import com.vladmeh.choosing.model.Restaurant;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.testdata.RestaurantTestData.*;
import static com.vladmeh.choosing.web.AbstractControllerTest.REST_URL;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 29.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */


public class LunchTestData {
    public static final String LUNCH_URL = REST_URL + "/lunch/";

    //RESTAURANT_0
    public static final Lunch LUNCH_0 = new Lunch(0L, "Макчикен", LocalDate.now(), 100, RESTAURANT_0);
    public static final Lunch LUNCH_1 = new Lunch(1L, "Kebab", LocalDate.of(2018, 5, 23), 75, RESTAURANT_0);
    //RESTAURANT_1
    public static final Lunch LUNCH_2 = new Lunch(2L, "Двойной Чизбургер", LocalDate.now(), 118, RESTAURANT_1);
    public static final Lunch LUNCH_3 = new Lunch(3L, "Цезарь Ролл", LocalDate.of(2018, 5, 23), 164, RESTAURANT_1);
    //RESTAURANT_2
    public static final Lunch LUNCH_4 = new Lunch(4L, "Танкобургер", LocalDate.now(), 120, RESTAURANT_2);
    public static final Lunch LUNCH_5 = new Lunch(5L, "Чиззи Чикен Фри", LocalDate.of(2018, 5, 23), 80, RESTAURANT_2);

    public static Map<String, Object> getStringObjectMapLunch(Restaurant restaurant, LocalDate date, String name, int price) {
        return new HashMap<>() {{
            put("restaurant", RESTAURANT_URL + restaurant.getId());
            put("date", date);
            put("name", name);
            put("price", price);
        }};
    }
}
