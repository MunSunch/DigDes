package com.munsun.system_projects.business.integ;

import com.munsun.system_projects.business.Container;
import com.munsun.system_projects.business.model.CommandEmployees;
import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.business.service.ProjectService;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.RoleCommand;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CommandServiceIntegTest extends Container {
    @Autowired
    private CommandService serviceCommand;
    @Autowired
    private ProjectService serviceProject;
    @Autowired
    private EmployeeService serviceEmployee;

    @Test
    public void testCreateCommandByProject() throws Exception {
        ProjectDtoIn testProject = new ProjectDtoIn();
        testProject.setName("testProject");
        var project = serviceProject.createProject(testProject);
        CommandDtoIn testCommand = new CommandDtoIn();
        testCommand.setIdProject(project.getId());

        int idCommand = serviceCommand.createCommand(testCommand).getId();
        var actual = serviceCommand.getCommandById(idCommand);

        assertAll(
                ()->assertNotEquals(0, actual.getId()),
                ()->assertNotEquals(null, actual.getCode()),
                ()->assertEquals(testCommand.getIdProject(), actual.getProject().getId())
        );
    }

    @Test
    public void testCreateCommandByNotExistsProject() throws Exception {
        CommandDtoIn testCommand = new CommandDtoIn();
        testCommand.setIdProject(100);

        assertThrowsExactly(ProjectNotFoundException.class, ()->{
            serviceCommand.createCommand(testCommand);
        });
    }

    @Test
    public void testAddEmployeeToNotExistsCommand() throws Exception {
        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
        testEmployee.setName("test");
        testEmployee.setLastname("test");
        testEmployee.setPytronymic("test");
        testEmployee.setEmail("test");
        testEmployee.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        var commandEmployees = new CommandEmployeesDtoIn();
        commandEmployees.setIdCommand(100);
        commandEmployees.setIdEmployee(idEmployee);
        commandEmployees.setRoleCommand(RoleCommand.ANALYST);

        assertThrowsExactly(CommandNotFoundException.class, ()-> {
            serviceCommand.addEmployeeCommand(commandEmployees);
        });
    }

    @Test
    public void testAddNotExistsEmployeeToCommand() throws Exception {
        ProjectDtoIn testProject = new ProjectDtoIn();
        testProject.setName("testProject");
        var project = serviceProject.createProject(testProject);
        CommandDtoIn testCommand = new CommandDtoIn();
        testCommand.setIdProject(project.getId());
        int idCommand = serviceCommand.createCommand(testCommand).getId();

        var commandEmployees = new CommandEmployeesDtoIn();
        commandEmployees.setIdCommand(idCommand);
        commandEmployees.setIdEmployee(100);
        commandEmployees.setRoleCommand(RoleCommand.ANALYST);

        assertThrowsExactly(EmployeeNotFoundException.class, ()-> {
            serviceCommand.addEmployeeCommand(commandEmployees);
        });
    }

    @Test
    public void testAddDuplicateEmployeeToCommand() throws Exception {
        ProjectDtoIn testProject = new ProjectDtoIn();
            testProject.setName("testProject");
        int idProject = serviceProject.createProject(testProject).getId();

        CommandDtoIn testCommand = new CommandDtoIn();
            testCommand.setIdProject(idProject);
        int idCommand = serviceCommand.createCommand(testCommand).getId();

        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
            testEmployee.setName("test");
            testEmployee.setLastname("test");
            testEmployee.setPytronymic("test");
            testEmployee.setEmail("test");
            testEmployee.setPostEmployee(PostEmployee.MANAGER);
            AccountDtoIn accountTest = new AccountDtoIn();
            accountTest.setLogin("test");
            accountTest.setPassword("test");
            testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        CommandEmployeesDtoIn commandEmployeesDtoIn1 = new CommandEmployeesDtoIn();
        commandEmployeesDtoIn1.setIdCommand(idCommand);
        commandEmployeesDtoIn1.setIdEmployee(idEmployee);
        commandEmployeesDtoIn1.setRoleCommand(RoleCommand.DESIGNER);

        CommandEmployeesDtoIn commandEmployeesDtoIn2 = new CommandEmployeesDtoIn();
        commandEmployeesDtoIn2.setIdCommand(idCommand);
        commandEmployeesDtoIn2.setIdEmployee(idEmployee);
        commandEmployeesDtoIn2.setRoleCommand(RoleCommand.ANALYST);

        serviceCommand.addEmployeeCommand(commandEmployeesDtoIn1);

        assertThrowsExactly(CommandEmployeeDuplicateException.class, ()->{
            serviceCommand.addEmployeeCommand(commandEmployeesDtoIn2);
        });
    }

    @Test
    public void testRemoveEmployeeByNotExistsProject() throws Exception {
        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
        testEmployee.setName("test");
        testEmployee.setLastname("test");
        testEmployee.setPytronymic("test");
        testEmployee.setEmail("test");
        testEmployee.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        assertThrowsExactly(ProjectNotFoundException.class, ()-> {
            serviceCommand.removeEmployeeByProject(100, idEmployee);
        });
    }

    @Test
    public void testRemoveNotExistsEmployeeByProject() throws Exception {
        ProjectDtoIn testProject = new ProjectDtoIn();
        testProject.setName("testProject");
        var project = serviceProject.createProject(testProject);
        CommandDtoIn testCommand = new CommandDtoIn();
        testCommand.setIdProject(project.getId());

        int idCommand = serviceCommand.createCommand(testCommand).getId();

        assertThrowsExactly(EmployeeNotFoundException.class, ()-> {
            serviceCommand.removeEmployeeByProject(project.getId(), 100);
        });
    }

    @Test
    public void testRemoveEmployeeByProjectWithNotExistsCommandByProject() throws Exception{
        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
        testEmployee.setName("test");
        testEmployee.setLastname("test");
        testEmployee.setPytronymic("test");
        testEmployee.setEmail("test");
        testEmployee.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        ProjectDtoIn testProject = new ProjectDtoIn();
        testProject.setName("testProject");
        int idProject = serviceProject.createProject(testProject).getId();

        assertThrowsExactly(CommandNotFoundException.class, ()-> {
            serviceCommand.removeEmployeeByProject(idProject, idEmployee);
        });
    }

    @Test
    public void testRemoveEmployeeByProjectWithNotExistsCommandEmployee() throws Exception{
        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
        testEmployee.setName("test");
        testEmployee.setLastname("test");
        testEmployee.setPytronymic("test");
        testEmployee.setEmail("test");
        testEmployee.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        ProjectDtoIn testProject = new ProjectDtoIn();
        testProject.setName("testProject");
        int idProject = serviceProject.createProject(testProject).getId();
        CommandDtoIn testCommand = new CommandDtoIn();
        testCommand.setIdProject(idProject);
        serviceCommand.createCommand(testCommand);

        assertThrowsExactly(CommandEmployeeNotFoundException.class, ()-> {
            serviceCommand.removeEmployeeByProject(idProject, idEmployee);
        });
    }

    @Test
    public void testRemoveEmployeeByProject() throws Exception{
        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
        testEmployee.setName("test");
        testEmployee.setLastname("test");
        testEmployee.setPytronymic("test");
        testEmployee.setEmail("test");
        testEmployee.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        ProjectDtoIn testProject = new ProjectDtoIn();
        testProject.setName("testProject");
        int idProject = serviceProject.createProject(testProject).getId();
        CommandDtoIn testCommand = new CommandDtoIn();
        testCommand.setIdProject(idProject);
        int idCommand = serviceCommand.createCommand(testCommand).getId();

        var commandEmployee = new CommandEmployeesDtoIn();
        commandEmployee.setIdEmployee(idEmployee);
        commandEmployee.setIdCommand(idCommand);
        commandEmployee.setRoleCommand(RoleCommand.DESIGNER);
        serviceCommand.addEmployeeCommand(commandEmployee);

        serviceCommand.removeEmployeeByProject(idProject, idEmployee);

        assertDoesNotThrow(()->{
            serviceCommand.addEmployeeCommand(commandEmployee);
        });
    }

    @Test
    public void testGetEmployeesByProjectId() throws Exception {
        EmployeeDtoIn testEmployee = new EmployeeDtoIn();
            testEmployee.setName("test");
            testEmployee.setLastname("test");
            testEmployee.setPytronymic("test");
            testEmployee.setEmail("test");
            testEmployee.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
            accountTest.setLogin("test");
            accountTest.setPassword("test");
            testEmployee.setAccount(accountTest);
        int idEmployee = serviceEmployee.createEmployee(testEmployee).getId();

        ProjectDtoIn testProject = new ProjectDtoIn();
            testProject.setName("testProject");
        int idProject = serviceProject.createProject(testProject).getId();

        CommandDtoIn testCommand = new CommandDtoIn();
            testCommand.setIdProject(idProject);
        int idCommand = serviceCommand.createCommand(testCommand).getId();

        var commandEmployee = new CommandEmployeesDtoIn();
            commandEmployee.setIdEmployee(idEmployee);
            commandEmployee.setIdCommand(idCommand);
            commandEmployee.setRoleCommand(RoleCommand.DESIGNER);
        serviceCommand.addEmployeeCommand(commandEmployee);

        int expected = 1;
        var actual = serviceCommand.getEmployeesByProject(idProject).size();

        assertEquals(expected, actual);
    }
}
