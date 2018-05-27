package com.vladmeh.graduation.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.Server;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.SQLException;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 *
 * https://techdev.io/en/developer-blog/querying-the-embedded-h2-database-of-a-spring-boot-application
 */

@Configuration
public class AppConfig {

    /*
    // https://techdev.io/en/developer-blog/querying-the-embedded-h2-database-of-a-spring-boot-application
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("dev")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }*/

    //https://github.com/spring-projects/spring-boot/issues/1182
    /*@Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }*/
}