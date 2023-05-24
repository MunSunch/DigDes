package com.munsun.system_projects.app;

import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import com.munsun.system_projects.domain.config.ConfigDomain;
import com.munsun.system_projects.domain.service.impl.EmployeeServiceImpl;
import com.munsun.system_projects.dto.config.ConfigDTO;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ConfigDomain.class, ConfigDTO.class})
public class Application {
    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class);
        var service = context.getBean(EmployeeServiceImpl.class);


    }
}
