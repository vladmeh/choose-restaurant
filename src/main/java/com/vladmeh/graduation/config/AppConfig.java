package com.vladmeh.graduation.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.*;

import java.sql.SQLException;

/**
 *  https://techdev.io/en/developer-blog/querying-the-embedded-h2-database-of-a-spring-boot-application
 */

@Configuration
public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("dev")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}