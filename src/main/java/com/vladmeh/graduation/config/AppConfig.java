package com.vladmeh.graduation.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 24.05.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 * <p>
 * https://techdev.io/en/developer-blog/querying-the-embedded-h2-database-of-a-spring-boot-application
 */

@Configuration
public class AppConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
        mapperBuilder.
                serializationInclusion(JsonInclude.Include.NON_NULL).
                serializationInclusion(JsonInclude.Include.NON_EMPTY).
                build().
                setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE).
                setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return mapperBuilder;
    }
}