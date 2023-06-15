package com.munsun.system_projects.business.units;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Task;
import com.munsun.system_projects.business.repository.*;
import com.munsun.system_projects.business.service.TaskService;
import com.munsun.system_projects.dto.entity.in.TaskDtoIn;
import com.munsun.system_projects.dto.entity.out.TaskDtoOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TaskServiceUnitTest {
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private StatusTaskRepository statusTaskRepository;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private CommandRepository commandRepository;
    @MockBean
    private CommandEmployeeRepository commandEmployeeRepository;

    @SpyBean
    private Mapper<Task, TaskDtoOut> mapperTask;

    @Autowired
    private TaskService service;

    @Test
    public void testCreateTask() {

    }
}
