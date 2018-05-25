package com.vladmeh.graduation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 25.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@Entity
@Table(name = "menu_dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "unique_menu_name_idx")})
public class Dishes extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    private Menu menu;

    public Dishes() {
    }

    public Dishes(Long id, String name, @NotNull Integer price) {
        super(id, name);
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dishes{" +
                "id=" + getId() +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
