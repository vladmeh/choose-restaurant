package com.vladmeh.graduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity extends AbstractPersistable<Long> {

    AbstractBaseEntity() {
    }

    AbstractBaseEntity(Long id) {
        setId(id);
    }

    @JsonIgnore
    public boolean isNew() {
        return super.isNew();
    }

}
