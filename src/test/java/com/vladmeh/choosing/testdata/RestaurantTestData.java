package com.vladmeh.choosing.testdata;

import com.vladmeh.choosing.model.Restaurant;

import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.web.AbstractControllerTest.REST_URL;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 07.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */


public class RestaurantTestData {
    public static final String RESTAURANT_URL = REST_URL + "/restaurants/";

    public static final Restaurant RESTAURANT_0 = new Restaurant(0L, "McDonalds");
    public static final Restaurant RESTAURANT_1 = new Restaurant(1L, "Burger King");
    public static final Restaurant RESTAURANT_2 = new Restaurant(2L, "KFC");

    public static Map<String, Object> getStringObjectMapRestaurant(String name) {
        return new HashMap<String, Object>() {{
            put("name", name);
        }};
    }
}
