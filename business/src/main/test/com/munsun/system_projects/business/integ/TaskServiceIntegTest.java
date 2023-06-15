package com.munsun.system_projects.business.integ;

import com.munsun.system_projects.business.Container;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.business.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TaskServiceIntegTest extends Container {
    @Autowired
    private TaskService serviceTask;
    @Autowired
    private EmployeeService serviceEmployee;

}
