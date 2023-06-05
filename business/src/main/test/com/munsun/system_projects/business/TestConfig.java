package com.munsun.system_projects.business;

import com.munsun.system_projects.business.config.ConfigDomain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Import(ConfigDomain.class)
@PropertySource("classpath:application-business.properties")
public class TestConfig {}