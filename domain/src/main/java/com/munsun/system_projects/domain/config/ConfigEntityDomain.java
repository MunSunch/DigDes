package com.munsun.system_projects.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"src/main/java/com/munsun/system_projects/domain/model",
                            "src/main/java/com/munsun/system_projects/domain/repository"})
public class ConfigEntityDomain {}