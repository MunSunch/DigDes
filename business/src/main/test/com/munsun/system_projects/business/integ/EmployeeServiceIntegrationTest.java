package com.munsun.system_projects.business.integ;

import com.munsun.system_projects.business.Container;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import exp.AccountDuplicateException;
import exp.UserDuplicateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceIntegrationTest extends Container {
    @Autowired
    private EmployeeService service;

    @DisplayName("Сохранение сотрудника, которого нет в БД")
    @Test
    public void testCreateNotExistsEmployee() throws Exception {
        EmployeeDTO expected = new EmployeeDTO();
            expected.setName("test");
            expected.setLastname("test");
            expected.setPytronymic("test");
            AccountDTO testAccount = new AccountDTO();
            testAccount.setLogin("test");
            testAccount.setPassword("test");
            expected.setAccount(testAccount);
            expected.setPostEmployee(PostEmployee.DESIGNER);
            expected.setEmail("test");
            expected.setStatusEmployee(StatusEmployee.REMOVED);

        var actual = service.createEmployee(expected);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getName(), actual.getName()),
                () -> Assertions.assertEquals(expected.getLastname(), actual.getLastname()),
                () -> Assertions.assertEquals(actual.getAccount().getId(), actual.getId()), //OneToOne
                () -> Assertions.assertEquals(expected.getAccount().getLogin(), actual.getAccount().getLogin()),
                () -> Assertions.assertEquals(expected.getAccount().getPassword(), actual.getAccount().getPassword()),
                () -> Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                () -> Assertions.assertEquals(expected.getPostEmployee(), actual.getPostEmployee()),
                () -> Assertions.assertEquals(actual.getStatusEmployee(), StatusEmployee.ACTIVE)
        );
    }

    @DisplayName("Сохранение сотрудника, который уже есть в БД")
    @Test
    public void testCreateExistsEmployee() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setName("test");
            testEmployee1.setLastname("test");
            testEmployee1.setPytronymic("test");
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setLogin("test");
            testAccount1.setPassword("test");
            testEmployee1.setAccount(testAccount1);
            testEmployee1.setPostEmployee(PostEmployee.DESIGNER);
            testEmployee1.setEmail("test");
            testEmployee1.setStatusEmployee(StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setName("test");
            testEmployee2.setLastname("test");
            testEmployee2.setPytronymic("test");
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setLogin("test");
            testAccount2.setPassword("test");
            testEmployee2.setAccount(testAccount2);
            testEmployee2.setPostEmployee(PostEmployee.TESTER);
            testEmployee2.setEmail("test");
            testEmployee2.setStatusEmployee(StatusEmployee.REMOVED);

        var result = service.createEmployee(testEmployee1);

        Assertions.assertThrowsExactly(UserDuplicateException.class, () -> {
            service.createEmployee(testEmployee2);
        });
    }

    @DisplayName("Аккаунт(логин) уже существует")
    @Test
    public void testCreateEmployeeExistsAccount() throws Exception {
        EmployeeDTO testEmployeeDTO1 = new EmployeeDTO();
            testEmployeeDTO1.setId(0);
            testEmployeeDTO1.setName("test1");
            testEmployeeDTO1.setLastname("test1");
            testEmployeeDTO1.setPytronymic("test1");
            testEmployeeDTO1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccountDTO1 = new AccountDTO();
            testAccountDTO1.setId(0);
                testAccountDTO1.setLogin("test");
            testAccountDTO1.setPassword("test1");
            testEmployeeDTO1.setAccount(testAccountDTO1);
            testEmployeeDTO1.setEmail("test1");
            testEmployeeDTO1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployeeDTO2 = new EmployeeDTO();
            testEmployeeDTO2.setId(0);
            testEmployeeDTO2.setName("test2");
            testEmployeeDTO2.setLastname("test2");
            testEmployeeDTO2.setPytronymic("test2");
            testEmployeeDTO2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.DESIGNER);
            AccountDTO testAccountDTO2 = new AccountDTO();
            testAccountDTO2.setId(0);
                testAccountDTO2.setLogin("test");
            testAccountDTO2.setPassword("test2");
            testEmployeeDTO2.setAccount(testAccountDTO2);
            testEmployeeDTO2.setEmail("test2");
            testEmployeeDTO2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        var actual = service.createEmployee(testEmployeeDTO1);

        Assertions.assertThrowsExactly(AccountDuplicateException.class, () -> {
            service.createEmployee(testEmployeeDTO2);
        });
    }

    @DisplayName("Удаление по существующему id")
    @Test
    public void removeEmployeeExistsId() throws Exception {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
            testEmployeeDTO.setId(0);
            testEmployeeDTO.setName("test");
            testEmployeeDTO.setLastname("test");
            testEmployeeDTO.setPytronymic("test");
            testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccountDTO = new AccountDTO();
            testAccountDTO.setId(0);
            testAccountDTO.setLogin("test");
            testAccountDTO.setPassword("test");
            testEmployeeDTO.setAccount(testAccountDTO);
            testEmployeeDTO.setEmail("test");
            testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        var expected = service.createEmployee(testEmployeeDTO);
        var actual = service.removeEmployeeById(expected.getId());

        Assertions.assertAll(
                ()->Assertions.assertEquals(expected.getId(), actual.getId()),
                ()->Assertions.assertEquals(expected.getName(), actual.getName()),
                ()->Assertions.assertEquals(expected.getLastname(), actual.getLastname()),
                ()->Assertions.assertEquals(expected.getPytronymic(), actual.getPytronymic()),
                ()->Assertions.assertEquals(expected.getAccount().getId(), actual.getAccount().getId()),
                ()->Assertions.assertEquals(expected.getAccount().getLogin(), actual.getAccount().getLogin()),
                ()->Assertions.assertEquals(expected.getAccount().getPassword(), actual.getAccount().getPassword()),
                ()->Assertions.assertEquals(expected.getPostEmployee(), actual.getPostEmployee()),
                ()->Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                ()->Assertions.assertEquals(StatusEmployee.REMOVED.name(), actual.getStatusEmployee().name())
        );
    }

    @DisplayName("Получение сотрудника по существующему id")
    @Test
    public void testGetEmployeeByNotExistsId() throws Exception {
        EmployeeDTO testEmployee = new EmployeeDTO();
            testEmployee.setId(0);
            testEmployee.setName("test");
            testEmployee.setLastname("test");
            testEmployee.setPytronymic("test");
            testEmployee.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount = new AccountDTO();
            testAccount.setId(0);
            testAccount.setLogin("test");
            testAccount.setPassword("test");
            testEmployee.setAccount(testAccount);
            testEmployee.setEmail("test");
            testEmployee.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        var expected = service.createEmployee(testEmployee);
        var actual = service.getEmployeeById(expected.getId());

        Assertions.assertAll(
                ()->Assertions.assertEquals(expected.getId(), actual.getId()),
                ()->Assertions.assertEquals(expected.getName(), actual.getName()),
                ()->Assertions.assertEquals(expected.getLastname(), actual.getLastname()),
                ()->Assertions.assertEquals(expected.getPytronymic(), actual.getPytronymic()),
                ()->Assertions.assertEquals(expected.getAccount().getId(), actual.getAccount().getId()),
                ()->Assertions.assertEquals(expected.getAccount().getLogin(), actual.getAccount().getLogin()),
                ()->Assertions.assertEquals(expected.getAccount().getPassword(), actual.getAccount().getPassword()),
                ()->Assertions.assertEquals(expected.getPostEmployee(), actual.getPostEmployee()),
                ()->Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                ()->Assertions.assertEquals(expected.getStatusEmployee(), actual.getStatusEmployee())
        );
    }

    @DisplayName("Получение сотрудника по его учетной записи(логину)")
    @Test
    public void testGetEmployeeByAccount() throws Exception {
        EmployeeDTO testEmployee = new EmployeeDTO();
            testEmployee.setId(0);
            testEmployee.setName("test");
            testEmployee.setLastname("test");
            testEmployee.setPytronymic("test");
            testEmployee.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount = new AccountDTO();
            testAccount.setId(0);
            testAccount.setLogin("test");
            testAccount.setPassword("test");
            testEmployee.setAccount(testAccount);
            testEmployee.setEmail("test");
            testEmployee.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        var expected = service.createEmployee(testEmployee);
        var actual = service.getEmployeeByAccount("test");

        Assertions.assertAll(
                ()->Assertions.assertEquals(expected.getId(), actual.getId()),
                ()->Assertions.assertEquals(expected.getName(), actual.getName()),
                ()->Assertions.assertEquals(expected.getLastname(), actual.getLastname()),
                ()->Assertions.assertEquals(expected.getPytronymic(), actual.getPytronymic()),
                ()->Assertions.assertEquals(expected.getAccount().getId(), actual.getAccount().getId()),
                ()->Assertions.assertEquals(expected.getAccount().getLogin(), actual.getAccount().getLogin()),
                ()->Assertions.assertEquals(expected.getAccount().getPassword(), actual.getAccount().getPassword()),
                ()->Assertions.assertEquals(expected.getPostEmployee(), actual.getPostEmployee()),
                ()->Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                ()->Assertions.assertEquals(expected.getStatusEmployee(), actual.getStatusEmployee())
        );
    }

    @DisplayName("Поиск сотрудников по имени и активному статусу")
    @Test
    public void testFindEmployeesByStringName() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
                testEmployee1.setName("testName");
            testEmployee1.setLastname("test1");
            testEmployee1.setPytronymic("test1");
            testEmployee1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setId(0);
            testAccount1.setLogin("test1");
            testAccount1.setPassword("test1");
            testEmployee1.setAccount(testAccount1);
            testEmployee1.setEmail("test1");
            testEmployee1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setId(0);
                testEmployee2.setName("testName");
            testEmployee2.setLastname("test2");
            testEmployee2.setPytronymic("test2");
            testEmployee2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
            testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount2);
            testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        service.createEmployee(testEmployee1);
        service.createEmployee(testEmployee2);
        var actual = service.findEmployeesByString("testName");

        int expectedCountEmployee = 2;
        Assertions.assertEquals(expectedCountEmployee, actual.size());
    }

    @DisplayName("Поиск сотрудников по фамилии и активному статусу")
    @Test
    public void testFindEmployeesByStringLastname() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
            testEmployee1.setName("test1");
                testEmployee1.setLastname("testLastname");
            testEmployee1.setPytronymic("test1");
            testEmployee1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setId(0);
            testAccount1.setLogin("test1");
            testAccount1.setPassword("test1");
            testEmployee1.setAccount(testAccount1);
            testEmployee1.setEmail("test1");
            testEmployee1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setId(0);
            testEmployee2.setName("test2");
                testEmployee2.setLastname("test2");
            testEmployee2.setPytronymic("test2");
            testEmployee2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
            testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount2);
            testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        service.createEmployee(testEmployee1);
        service.createEmployee(testEmployee2);
        var actual = service.findEmployeesByString("testLastname");

        int expectedCountEmployee = 1;
        Assertions.assertEquals(expectedCountEmployee, actual.size());
    }

    @DisplayName("Поиск сотрудников по отчеству и активному статусу")
    @Test
    public void testFindEmployeesByStringPyronymic() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
            testEmployee1.setName("test1");
            testEmployee1.setLastname("test1");
                testEmployee1.setPytronymic("testPytronymic");
            testEmployee1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setId(0);
            testAccount1.setLogin("test1");
            testAccount1.setPassword("test1");
            testEmployee1.setAccount(testAccount1);
            testEmployee1.setEmail("test1");
            testEmployee1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setId(0);
            testEmployee2.setName("test2");
            testEmployee2.setLastname("test2");
                testEmployee2.setPytronymic("testPytronymic");
            testEmployee2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
            testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount2);
            testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        service.createEmployee(testEmployee1);
        service.createEmployee(testEmployee2);
        var actual = service.findEmployeesByString("testPytronymic");

        int expectedCountEmployee = 2;
        Assertions.assertEquals(expectedCountEmployee, actual.size());
    }

    @DisplayName("Поиск сотрудников по имейлу и активному статусу")
    @Test
    public void testFindEmployeesByStringEmail() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
            testEmployee1.setName("test1");
            testEmployee1.setLastname("test1");
                testEmployee1.setPytronymic("test1");
            testEmployee1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setId(0);
            testAccount1.setLogin("test1");
            testAccount1.setPassword("test1");
            testEmployee1.setAccount(testAccount1);
                testEmployee1.setEmail("testEmail");
            testEmployee1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setId(0);
            testEmployee2.setName("test2");
            testEmployee2.setLastname("test2");
                testEmployee2.setPytronymic("testPytronymic");
            testEmployee2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
            testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount2);
                testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        service.createEmployee(testEmployee1);
        service.createEmployee(testEmployee2);
        var actual = service.findEmployeesByString("testEmail");

        int expectedCountEmployee = 1;
        Assertions.assertEquals(expectedCountEmployee, actual.size());
    }

    @DisplayName("Поиск сотрудников по логину и активному статусу")
    @Test
    public void testFindEmployeesByStringLogin() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
            testEmployee1.setName("test1");
            testEmployee1.setLastname("test1");
            testEmployee1.setPytronymic("test1");
            testEmployee1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setId(0);
                testAccount1.setLogin("testLogin");
            testAccount1.setPassword("test1");
            testEmployee1.setAccount(testAccount1);
            testEmployee1.setEmail("test1");
            testEmployee1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setId(0);
            testEmployee2.setName("test2");
            testEmployee2.setLastname("test2");
            testEmployee2.setPytronymic("test2");
            testEmployee2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
                testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount2);
            testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        service.createEmployee(testEmployee1);
        service.createEmployee(testEmployee2);
        var actual = service.findEmployeesByString("testLogin");

        int expectedCountEmployee = 1;
        Assertions.assertEquals(expectedCountEmployee, actual.size());
    }

    @DisplayName("Редактирование сотрудника по идентификатору")
    @Test
    public void setEmployeeById() throws Exception {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
            testEmployee1.setName("test1");
            testEmployee1.setLastname("test1");
            testEmployee1.setPytronymic("test1");
            testEmployee1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount1 = new AccountDTO();
            testAccount1.setId(0);
            testAccount1.setLogin("test1");
            testAccount1.setPassword("test1");
            testEmployee1.setAccount(testAccount1);
            testEmployee1.setEmail("test1");
            testEmployee1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployee2 = new EmployeeDTO();
            testEmployee2.setId(0);
            testEmployee2.setName("test2");
            testEmployee2.setLastname("test2");
            testEmployee2.setPytronymic("test2");
            testEmployee2.setPostEmployee(PostEmployee.TESTER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
            testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount2);
            testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        var expected = service.createEmployee(testEmployee1);
        var actual = service.setEmployee(expected.getId(), testEmployee2);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getName(), actual.getName()),
                () -> Assertions.assertEquals(expected.getLastname(), actual.getLastname()),
                () -> Assertions.assertEquals(expected.getPytronymic(), actual.getPytronymic()),
                () -> Assertions.assertEquals(expected.getPostEmployee(), actual.getPostEmployee()),
                () -> Assertions.assertEquals(expected.getAccount().getId(), actual.getAccount().getId()),
                () -> Assertions.assertEquals(expected.getAccount().getLogin(), actual.getAccount().getLogin()),
                () -> Assertions.assertEquals(expected.getAccount().getPassword(), actual.getAccount().getPassword()),
                () -> Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                () -> Assertions.assertEquals(expected.getStatusEmployee(), actual.getStatusEmployee())
        );
    }
}