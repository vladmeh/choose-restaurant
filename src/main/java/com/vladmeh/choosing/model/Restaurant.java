package com.vladmeh.choosing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "unique_restaurant_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Where(clause = "lunch_date = today()")
    private List<Lunch> lunch;

    public Restaurant(Long id, String name) {
        super(id, name);
    }
}
