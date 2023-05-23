package com.munsun.system_projects.app;

import com.munsun.system_projects.domain.config.ConfigDomain;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;
import com.munsun.system_projects.domain.service.EmployeeService;
import com.munsun.system_projects.dto.config.ConfigDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ConfigDomain.class, ConfigDTO.class})
public class Application {
    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class);
        var service = context.getBean(EmployeeService.class);

        Employee employee = new Employee();
        employee.setName("Test");
        employee.setLastname("test");
        PostEmployee post = new PostEmployee();
        post.setName("SEO");
        employee.setPostEmployee(post);
        Account account = new Account();
        account.setLogin("test");
        account.setPassword("test");
        employee.setAccount(account);
        StatusEmployee status = new StatusEmployee();
        status.setName("ACTIVE");
        employee.setStatusEmployee(status);

        //var s = service.findById(1);
        service.createEmployee(employee);
    }
}
