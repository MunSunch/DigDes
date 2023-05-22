package com.munsun.system_projects.app;

import com.munsun.system_projects.domain.config.ConfigEntityDomain;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.dto.ConfigDTO;
import com.munsun.system_projects.dto.EmployeeDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ConfigEntityDomain.class, ConfigDTO.class})
public class Application {
    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class);

        var e = context.getBean(EmployeeDTO.class);
    }
}
