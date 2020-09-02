package com.vladmeh.choosing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 01.09.2020.
 * @link https://github.com/vladmeh/choose-restaurant
 */

@NoRepositoryBean
public interface AdminSecurityRepository<T> extends JpaRepository<T, Long> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends T> S save(S entity);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(T entity);
}
