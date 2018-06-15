package com.vladmeh.choosing.testdata;

import com.vladmeh.choosing.model.Menu;
import com.vladmeh.choosing.model.Restaurant;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.testdata.RestaurantTestData.*;
import static com.vladmeh.choosing.web.AbstractControllerTest.REST_URL;

public class MenuTestData {
    public static final String MENU_URL = REST_URL + "/menu/";

    //RESTAURANT_0
    public static final Menu MENU_0 = new Menu(0L, RESTAURANT_0, LocalDate.now());
    public static final Menu MENU_1 = new Menu(1L, RESTAURANT_0, LocalDate.of(2018, 5, 23));
    //RESTAURANT_1
    public static final Menu MENU_2 = new Menu(2L, RESTAURANT_1, LocalDate.now());
    public static final Menu MENU_3 = new Menu(3L, RESTAURANT_1, LocalDate.of(2018, 5, 23));
    //RESTAURANT_2
    public static final Menu MENU_4 = new Menu(4L, RESTAURANT_2, LocalDate.now());
    public static final Menu MENU_5 = new Menu(5L, RESTAURANT_2, LocalDate.of(2018, 5, 23));

    public static Map<String, Object> getStringObjectMapMenu(Restaurant restaurant, LocalDate date) {
        return new HashMap<String, Object>() {{
            put("restaurant", RESTAURANT_URL + restaurant.getId());
            put("date", date);
        }};
    }

    public static Map<String, Object> getStringObjectMapMenu(Restaurant restaurant) {
        return new HashMap<String, Object>() {{
            put("restaurant", RESTAURANT_URL + restaurant.getId());
        }};
    }

    public static Map<String, Object> getStringObjectMapMenu(int restaurantId) {
        return new HashMap<String, Object>() {{
            put("restaurant", RESTAURANT_URL + restaurantId);
        }};
    }
}
