package com.vladmeh.graduation.model;

import javax.persistence.*;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        return "AbstractBaseEntity{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBaseEntity)) return false;

        AbstractBaseEntity that = (AbstractBaseEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
