package com.munsun.system_projects.app;

import com.munsun.system_projects.domain.config.ConfigDomain;
import com.munsun.system_projects.domain.service.impl.EmployeeServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ConfigDomain.class})
public class Application implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
