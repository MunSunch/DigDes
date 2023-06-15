package com.munsun.system_projects.business.integ;

import com.munsun.system_projects.business.Container;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import com.munsun.system_projects.commons.exceptions.AccountDuplicateException;
import com.munsun.system_projects.commons.exceptions.EmployeeDuplicateException;
import com.munsun.system_projects.commons.exceptions.EmployeeNotFoundException;
import com.munsun.system_projects.dto.entity.in.AccountDtoIn;
import com.munsun.system_projects.dto.entity.in.EmployeeDtoIn;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceIntegTest extends Container {
    @Autowired
    private EmployeeService service;

    @Test
    public void testCreateNotExistsEmployee() throws Exception {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        var emp = service.createEmployee(test);
        var actual = service.getEmployeeById(emp.getId());

        assertAll(
                () -> assertNotEquals(0, actual.getId()),
                () -> assertEquals(test.getName(), actual.getName()),
                () -> assertEquals(test.getLastname(), actual.getLastname()),
                () -> assertEquals(test.getPytronymic(), actual.getPytronymic()),
                () -> assertEquals(test.getEmail(), actual.getEmail()),
                () -> assertEquals(test.getPostEmployee(), actual.getPostEmployee()),
                () -> assertEquals(StatusEmployee.ACTIVE, actual.getStatusEmployee())
        );
    }

    @Test
    public void testCreateExistsEmployee() throws Exception {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        service.createEmployee(test);

        assertThrowsExactly(EmployeeDuplicateException.class, () -> {
            service.createEmployee(test);
        });
    }

    @Test
    public void testCreateExistsAccountEmployee() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("testAccountDuplicateException");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("test2");
        test2.setPytronymic("test2");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("testAccountDuplicateException");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest);

        service.createEmployee(test1);

        assertThrowsExactly(AccountDuplicateException.class, () -> {
            service.createEmployee(test2);
        });
    }

    @Test
    public void testSetEmployee() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test1");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("test2");
        test2.setPytronymic("test2");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        accountTest.setLogin("test2");
        accountTest.setPassword("test2");
        test2.setAccount(accountTest);

        var emp = service.createEmployee(test1);
        service.setEmployee(emp.getId(), test2);
        var actual = service.getEmployeeById(emp.getId());

        assertAll(
                () -> assertEquals(test2.getName(),actual.getName()),
                () -> assertEquals(test2.getLastname(),actual.getLastname()),
                () -> assertEquals(test2.getPytronymic(),actual.getPytronymic()),
                () -> assertEquals(test2.getPostEmployee().name(),actual.getPostEmployee().name()),
                () -> assertEquals(test2.getAccount().getLogin(),actual.getAccount().getLogin()),
                () -> assertEquals(test2.getEmail(), actual.getEmail()),
                () -> assertEquals(emp.getStatusEmployee().name(), actual.getStatusEmployee().name())
        );
    }

    @Test
    public void testDeleteByNotExistsId() throws Exception {
        assertThrowsExactly(EmployeeNotFoundException.class, ()->{
            service.removeEmployeeById(0);
        });
    }

    @Test
    public void testDeleteById() throws Exception {
        EmployeeDtoIn test = new EmployeeDtoIn();
        test.setName("test");
        test.setLastname("test");
        test.setPytronymic("test");
        test.setEmail("test");
        test.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test");
        accountTest.setPassword("test");
        test.setAccount(accountTest);

        var emp = service.createEmployee(test);
        service.removeEmployeeById(emp.getId());
        var actual = service.getEmployeeById(emp.getId());

        assertEquals(StatusEmployee.REMOVED, actual.getStatusEmployee());
    }

    @Test
    public void testFindEmployeesByStringLastname() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("testLastname");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest1 = new AccountDtoIn();
        accountTest1.setLogin("test1");
        accountTest1.setPassword("test1");
        test1.setAccount(accountTest1);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("testLastname");
        test2.setPytronymic("test2");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("test2");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest2);

        EmployeeDtoIn test3 = new EmployeeDtoIn();
        test3.setName("test3");
        test3.setLastname("test3");
        test3.setPytronymic("test3");
        test3.setEmail("test3");
        test3.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest3 = new AccountDtoIn();
        accountTest3.setLogin("test3");
        accountTest3.setPassword("test3");
        test3.setAccount(accountTest3);

        service.createEmployee(test1);
        service.createEmployee(test2);
        service.createEmployee(test3);
        int expected = 2;
        var actual = service.findEmployeesByString("testLastname").size();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindEmployeesByStringName() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("testName");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest1 = new AccountDtoIn();
        accountTest1.setLogin("test1");
        accountTest1.setPassword("test1");
        test1.setAccount(accountTest1);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("testLastname");
        test2.setPytronymic("test2");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("test2");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest2);

        EmployeeDtoIn test3 = new EmployeeDtoIn();
        test3.setName("test3");
        test3.setLastname("test3");
        test3.setPytronymic("test3");
        test3.setEmail("test3");
        test3.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest3 = new AccountDtoIn();
        accountTest3.setLogin("test3");
        accountTest3.setPassword("test3");
        test3.setAccount(accountTest3);

        service.createEmployee(test1);
        service.createEmployee(test2);
        service.createEmployee(test3);
        int expected = 1;
        var actual = service.findEmployeesByString("testName").size();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindEmployeesByStringPytronymic() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("testPytronymic");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test1");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("test2");
        test2.setPytronymic("testPytronymic");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("test2");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest2);

        EmployeeDtoIn test3 = new EmployeeDtoIn();
        test3.setName("test3");
        test3.setLastname("test3");
        test3.setPytronymic("testPytronymic");
        test3.setEmail("test3");
        test3.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest3 = new AccountDtoIn();
        accountTest3.setLogin("test3");
        accountTest3.setPassword("test3");
        test3.setAccount(accountTest3);

        service.createEmployee(test1);
        service.createEmployee(test2);
        service.createEmployee(test3);
        int expected = 3;
        var actual = service.findEmployeesByString("testPytronymic").size();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindEmployeesByStringEmail() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test1");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("test2");
        test2.setPytronymic("test1");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("test2");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest2);

        EmployeeDtoIn test3 = new EmployeeDtoIn();
        test3.setName("test3");
        test3.setLastname("test3");
        test3.setPytronymic("test1");
        test3.setEmail("test3");
        test3.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest3 = new AccountDtoIn();
        accountTest3.setLogin("test3");
        accountTest3.setPassword("test3");
        test3.setAccount(accountTest3);

        service.createEmployee(test1);
        service.createEmployee(test2);
        service.createEmployee(test3);
        int expected = 0;
        var actual = service.findEmployeesByString("testEmail").size();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindEmployeesByStringLogin() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test1");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("test2");
        test2.setPytronymic("test1");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("test2");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest2);

        EmployeeDtoIn test3 = new EmployeeDtoIn();
        test3.setName("test3");
        test3.setLastname("test3");
        test3.setPytronymic("test1");
        test3.setEmail("test3");
        test3.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest3 = new AccountDtoIn();
        accountTest3.setLogin("testLogin");
        accountTest3.setPassword("test3");
        test3.setAccount(accountTest3);

        service.createEmployee(test1);
        service.createEmployee(test2);
        service.createEmployee(test3);
        int expected = 1;
        var actual = service.findEmployeesByString("testLogin").size();

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveByIdNotExistsEmployee() {
        assertThrowsExactly(EmployeeNotFoundException.class, () -> {
            service.removeEmployeeById(100);
        });
    }

    @Test
    public void testRemoveById() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test1");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        var emp = service.createEmployee(test1);
        service.removeEmployeeById(emp.getId());
        var expected = StatusEmployee.REMOVED;
        var actual = service.getEmployeeById(emp.getId()).getStatusEmployee();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetEmployees() throws Exception {
        EmployeeDtoIn test1 = new EmployeeDtoIn();
        test1.setName("test1");
        test1.setLastname("test1");
        test1.setPytronymic("test1");
        test1.setEmail("test1");
        test1.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest = new AccountDtoIn();
        accountTest.setLogin("test1");
        accountTest.setPassword("test1");
        test1.setAccount(accountTest);

        EmployeeDtoIn test2 = new EmployeeDtoIn();
        test2.setName("test2");
        test2.setLastname("test2");
        test2.setPytronymic("test1");
        test2.setEmail("test2");
        test2.setPostEmployee(PostEmployee.MANAGER);
        AccountDtoIn accountTest2 = new AccountDtoIn();
        accountTest2.setLogin("test2");
        accountTest2.setPassword("test2");
        test2.setAccount(accountTest2);

        service.createEmployee(test1);
        service.createEmployee(test2);
        int expected = 3;
        var actual = service.getAllEmployees().size();

        assertEquals(expected, actual);
    }
}
