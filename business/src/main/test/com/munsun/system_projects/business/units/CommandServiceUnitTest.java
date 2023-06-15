package com.munsun.system_projects.business.units;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.CommandEmployees;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.repository.*;
import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.dto.entity.out.CommandDtoOut;
import com.munsun.system_projects.dto.entity.out.CommandEmployeesDtoOut;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CommandServiceUnitTest {
    @SpyBean
    private Mapper<CommandEmployees, CommandEmployeesDtoOut> mapperCommandEmployees;
    @SpyBean
    private Mapper<Command, CommandDtoOut> mapperCommand;
    @SpyBean
    private Mapper<Employee, EmployeeDtoOut> mapperEmployee;

    @MockBean
    private CommandRepository commandRepository;
    @MockBean
    private CommandEmployeeRepository commandEmployeeRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private RoleCommandRepository roleCommandRepository;
    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private CommandService service;

    @Test
    public void testCreateNull() {
        assertThrowsExactly(NullPointerException.class, ()->{
            service.createCommand(null);
        });
    }

    @Test
    public void testAddNull() {
        assertThrowsExactly(NullPointerException.class, ()->{
            service.addEmployeeCommand(null);
        });
    }
}
