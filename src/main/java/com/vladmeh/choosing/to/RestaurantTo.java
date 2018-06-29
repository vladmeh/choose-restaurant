package com.vladmeh.choosing.to;

import com.vladmeh.choosing.model.Lunch;
import com.vladmeh.choosing.model.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 29.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */

@NoArgsConstructor
@Data
public class RestaurantTo {
    private String name;
    List<LunchTo> lunches;

    public RestaurantTo (Restaurant restaurant, List<LunchTo> lunches){
        this.name = restaurant.getName();
        this.lunches = lunches;
    }
}
