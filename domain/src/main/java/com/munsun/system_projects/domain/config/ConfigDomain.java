package com.munsun.system_projects.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.munsun.system_projects.domain.model")
@EnableJpaRepositories(basePackages = "com.munsun.system_projects.domain.repository")
@ComponentScan(basePackages = {"com.munsun.system_projects.domain.service",
                               "com.munsun.system_projects.domain.mapping"})
public class ConfigDomain {}