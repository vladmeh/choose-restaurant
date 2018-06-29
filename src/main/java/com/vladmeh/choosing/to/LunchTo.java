package com.vladmeh.choosing.to;

import com.vladmeh.choosing.model.Lunch;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 29.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */

@Data
@NoArgsConstructor
public class LunchTo {
    private String name;
    private Integer price;

    public LunchTo(Lunch lunch) {
        this.name = lunch.getName();
        this.price = lunch.getPrice();
    }
}
