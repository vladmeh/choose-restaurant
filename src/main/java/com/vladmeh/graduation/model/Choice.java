package com.vladmeh.graduation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 27.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@Entity
@Table(name = "choice_restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "choice_date"}, name = "unique_user_choice_date_idx")})
public class Choice extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + getId() +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", date=" + date +
                '}';
    }
}
