package com.vladmeh.choosing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 27.05.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "choice_restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "choice_date"}, name = "unique_user_choice_date_idx")})
public class Choice extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "choice_date", nullable = false)
    @NotNull
    private LocalDate date;

    public Choice() {
    }

    public Choice(@NotNull User user, @NotNull Restaurant restaurant, @NotNull LocalDate date) {
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }
}
