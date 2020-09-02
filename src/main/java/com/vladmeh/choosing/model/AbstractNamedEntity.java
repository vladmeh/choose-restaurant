package com.vladmeh.choosing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 */

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name")
    String name;

    AbstractNamedEntity() {
    }

    AbstractNamedEntity(Long id, String name) {
        super(id);
        this.name = name;
    }
}
