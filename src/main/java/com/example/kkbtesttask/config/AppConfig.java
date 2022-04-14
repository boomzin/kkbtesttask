package com.example.kkbtesttask.config;

import com.example.kkbtesttask.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.Server;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Configuration
@EnableCaching
public class AppConfig {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppConfig.class);
    private final ObjectMapper objectMapper;

    public AppConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @PostConstruct
    void setMapper() {
        JsonUtil.setObjectMapper(objectMapper);
    }
}