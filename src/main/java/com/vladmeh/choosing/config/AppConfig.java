package com.vladmeh.choosing.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 08.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
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
