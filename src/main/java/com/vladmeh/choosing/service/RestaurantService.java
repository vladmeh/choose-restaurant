package com.vladmeh.choosing.service;

import com.vladmeh.choosing.model.Restaurant;
import com.vladmeh.choosing.to.RestaurantTo;

public interface RestaurantService {
    RestaurantTo getWithLunchToday(Restaurant restaurant);
}
