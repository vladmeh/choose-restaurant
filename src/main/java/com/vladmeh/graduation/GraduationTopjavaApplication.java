package com.vladmeh.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class GraduationTopjavaApplication {
    public static void main(String... args){
        SpringApplication.run(GraduationTopjavaApplication.class, args);
    }
}
