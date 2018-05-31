package com.vladmeh.graduation.config;

import com.vladmeh.graduation.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @autor mvl on 31.05.2018.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{bcrypt}$2a$04$/iBvUE.WM/3jvqvel9iAwOuWYndBJ6OdeeeU0kl8y3huzNqoo7KUW")
                .roles("ROLE_USER").build());
        manager.createUser(User.withUsername("admin").password("{bcrypt}$2a$04$x/5BKnvD16nEuMUcereYWevOyOuzCP9B41gk1NHhkN9mOMnP.EREq")
                .roles("ROLE_USER","ROLE_ADMIN").build());
        return manager;
    }*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                    .password("{bcrypt}$2a$04$/iBvUE.WM/3jvqvel9iAwOuWYndBJ6OdeeeU0kl8y3huzNqoo7KUW")
                    .roles("USER")
                .and()
                .withUser("admin")
                    .password("{bcrypt}$2a$04$x/5BKnvD16nEuMUcereYWevOyOuzCP9B41gk1NHhkN9mOMnP.EREq")
                    .roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/api-dev/**").hasAuthority(Role.ROLE_USER.getAuthority()).anyRequest().authenticated()
                .antMatchers("/api-dev/**").authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
        //http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
