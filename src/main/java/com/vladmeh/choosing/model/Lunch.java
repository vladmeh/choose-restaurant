package com.vladmeh.choosing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "lunch", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "unique_lunch_name_idx")})
public class Lunch extends AbstractNamedEntity {

    @NotNull
    @Column(name = "lunch_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Lunch(Long id, String name, @NotNull LocalDate date, @NotNull Integer price, @NotNull Restaurant restaurant) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Lunch(Lunch lunch) {
        this(lunch.getId(), lunch.getName(), lunch.getDate(), lunch.getPrice(), lunch.getRestaurant());
    }
}
