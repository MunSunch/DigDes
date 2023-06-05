package com.munsun.system_projects.business.tests.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.munsun.system_projects.business.model")
@EnableJpaRepositories(basePackages = "com.munsun.system_projects.business.repository")
@ComponentScan(basePackages = {"com.munsun.system_projects.business.service",
        "com.munsun.system_projects.business.mapping"})
public class ConfigDomain {}