package com.munsun.system_projects.business.units;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.repository.AccountRepository;
import com.munsun.system_projects.business.repository.EmployeeRepository;
import com.munsun.system_projects.business.repository.PostEmployeeRepository;
import com.munsun.system_projects.business.repository.StatusEmployeeRepository;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.exceptions.EmployeeEmptyFieldsException;
import com.munsun.system_projects.dto.entity.in.AccountDtoIn;
import com.munsun.system_projects.dto.entity.in.EmployeeDtoIn;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceUnitTest {
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private PostEmployeeRepository postEmployeeRepository;
    @MockBean
    private StatusEmployeeRepository statusEmployeeRepository;
    @MockBean
    private AccountRepository accountRepository;

    @SpyBean
    private Mapper<Employee, EmployeeDtoOut> mapperEmployee;

    @Autowired
    private EmployeeService service;

    @Test
    public void testCreateEmployeeWithEmptyName() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateEmployeeWithEmptyLastname() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateEmployeeWithEmptyLogin() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateEmployeeWithEmptyPassword() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateEmployeeWithEmptyPytronymic() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateEmployeeWithEmptyEmail() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateEmployeeWithNullPost() {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(null);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        assertThrowsExactly(EmployeeEmptyFieldsException.class, () -> {
            service.createEmployee(test);
        });
    }
}
