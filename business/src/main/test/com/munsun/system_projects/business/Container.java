package com.munsun.system_projects.business;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class Container {
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres");

    @BeforeAll
    public static void start() {
        container.start();
    }

    @AfterAll
    public static void stop() {
        container.stop();
    }

    @DynamicPropertySource
    public static void configProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driverClassName", container::getDriverClassName);
    }
}
