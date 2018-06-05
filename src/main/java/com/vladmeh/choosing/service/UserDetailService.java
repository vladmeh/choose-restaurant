package com.vladmeh.choosing.service;

import com.vladmeh.choosing.model.User;
import com.vladmeh.choosing.repository.UserRepository;
import com.vladmeh.choosing.userdetails.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/choosing-topjava
 * @link http://www.baeldung.com/spring-security-authentication-with-a-database
 */

@Service("userService")
public class UserDetailService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(UserDetailService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String login = email.toLowerCase();
        log.debug("auth {}", email);
        User user = userRepository.findByEmail(login);

        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }
        if (!user.isEnabled()) {
            throw new DisabledException("User " + login + " was not activated");
        }

        return new UserPrincipal(user);
    }
}
