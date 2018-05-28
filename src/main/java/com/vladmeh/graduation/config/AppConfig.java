package com.vladmeh.graduation.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 * <p>
 * https://techdev.io/en/developer-blog/querying-the-embedded-h2-database-of-a-spring-boot-application
 */

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper configureJackson(Jackson2ObjectMapperBuilder mapperBuilder) {
        return mapperBuilder.
                serializationInclusion(JsonInclude.Include.NON_NULL).
                serializationInclusion(JsonInclude.Include.NON_EMPTY).
                build().
                setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE).
                setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    //https://github.com/spring-projects/spring-boot/issues/1182
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

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
}