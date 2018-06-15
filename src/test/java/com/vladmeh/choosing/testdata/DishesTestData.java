package com.vladmeh.choosing.testdata;

import com.vladmeh.choosing.model.Dishes;
import com.vladmeh.choosing.model.Menu;

import java.util.HashMap;
import java.util.Map;

import static com.vladmeh.choosing.testdata.MenuTestData.MENU_0;
import static com.vladmeh.choosing.testdata.MenuTestData.MENU_URL;
import static com.vladmeh.choosing.web.AbstractControllerTest.REST_URL;

public class DishesTestData {
    public static final String DISHES_URL = REST_URL + "/dishes/";

    //RESTAURANT_0
    //MENU_0
    public static final Dishes DISHES_0 = new Dishes(0L, "Макчикен", 100);
    public static final Dishes DISHES_1 = new Dishes(1L, "Картофель фри", 75);
    //MENU_1
    public static final Dishes DISHES_2 = new Dishes(2L, "Двойной Чизбургер", 118);
    public static final Dishes DISHES_3 = new Dishes(3L, "Цезарь Ролл", 164);

    //RESTAURANT_1
    //MENU_2
    public static final Dishes DISHES_4 = new Dishes(4L, "Танкобургер", 120);
    public static final Dishes DISHES_5 = new Dishes(5L, "Чиззи Чикен Фри", 80);
    //MENU_3
    public static final Dishes DISHES_6 = new Dishes(6L, "Чикен Кинг", 150);
    public static final Dishes DISHES_7 = new Dishes(7L, "Кинг Завтрак", 200);

    //RESTAURANT_2
    //MENU_4
    public static final Dishes DISHES_8 = new Dishes(8L, "Брейкер", 99);
    public static final Dishes DISHES_9 = new Dishes(9L, "Твистер", 129);
    //MENU_5
    public static final Dishes DISHES_10 = new Dishes(10L, "БоксМастер", 149);
    public static final Dishes DISHES_11 = new Dishes(11L, "Терияки", 129);

    public static Map<String, Object> getStringObjectMapDishes(String name, Integer price) {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("price", price);
        }};
    }

    public static Map<String, Object> getStringObjectMapDishes(String name, Integer price, Menu menu) {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("price", price);
            put("menu", MENU_URL + MENU_0.getId());
        }};
    }
}
