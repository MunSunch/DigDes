package com.munsun.system_projects.business.units;

import com.munsun.system_projects.business.mapping.impl.AccountMapper;
import com.munsun.system_projects.business.mapping.impl.EmployeeMapper;
import com.munsun.system_projects.business.mapping.impl.PostEmployeeMapper;
import com.munsun.system_projects.business.mapping.impl.StatusEmployeeMapper;
import com.munsun.system_projects.business.model.PostEmployee;
import com.munsun.system_projects.business.model.StatusEmployee;
import com.munsun.system_projects.business.repository.AccountRepository;
import com.munsun.system_projects.business.repository.EmployeeRepository;
import com.munsun.system_projects.business.repository.PostEmployeeRepository;
import com.munsun.system_projects.business.repository.StatusEmployeeRepository;
import com.munsun.system_projects.business.service.impl.EmployeeServiceImpl;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import exp.AccountDuplicateException;
import exp.UserDuplicateException;
import exp.UserNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceTest {
    @SpyBean
    private AccountMapper mapperAccount;
    @SpyBean
    private PostEmployeeMapper mapperPost;
    @SpyBean
    private StatusEmployeeMapper mapperStatus;
    @SpyBean
    private EmployeeMapper mapperEmployee;

    @MockBean
    private PostEmployeeRepository postEmployeeRepository;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private StatusEmployeeRepository statusEmployeeRepository;
    @MockBean
    private EmployeeRepository employeeRepository;

    @InjectMocks
    @Autowired
    private EmployeeServiceImpl service;

    @DisplayName("Сохранение сотрудника, которого нет в БД")
    @Test
    public void testCreateNotExistsEmployee() throws Exception {
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
            testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.REMOVED);
        PostEmployee post = new PostEmployee();
        post.setName("MANAGER");
        StatusEmployee status = new StatusEmployee();
        status.setName("ACTIVE");

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(postEmployeeRepository.getPostEmployeeByName(anyString())).thenReturn(post);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.getStatusEmployeeByName(anyString())).thenReturn(status);
        when(employeeRepository.save(any())).thenReturn(null);
        var actual = service.createEmployee(testEmployeeDTO);

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(testEmployeeDTO.getName(), actual.getName()),
                () -> Assertions.assertEquals(testEmployeeDTO.getLastname(), actual.getLastname()),
                () -> Assertions.assertEquals(testEmployeeDTO.getPytronymic(), actual.getPytronymic()),
                () -> Assertions.assertEquals(testEmployeeDTO.getAccount().getLogin(), actual.getAccount().getLogin()),
                () -> Assertions.assertEquals(testEmployeeDTO.getAccount().getPassword(), actual.getAccount().getPassword()),
                () -> Assertions.assertEquals(testEmployeeDTO.getPostEmployee(), actual.getPostEmployee()),
                () -> Assertions.assertEquals(testEmployeeDTO.getEmail(), actual.getEmail()),
                () -> Assertions.assertEquals(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE, actual.getStatusEmployee())
        );
    }

    @DisplayName("Сохранение сотрудника, который присутствует в БД по ФИО и имейлу")
    @Test
    public void testCreateExistsEmployee() throws Exception {
        EmployeeDTO testEmployeeDTO1 = new EmployeeDTO();
            testEmployeeDTO1.setId(0);
            testEmployeeDTO1.setName("test");
            testEmployeeDTO1.setLastname("test");
            testEmployeeDTO1.setPytronymic("test");
            testEmployeeDTO1.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccountDTO1 = new AccountDTO();
            testAccountDTO1.setId(0);
            testAccountDTO1.setLogin("test");
            testAccountDTO1.setPassword("test");
            testEmployeeDTO1.setAccount(testAccountDTO1);
            testEmployeeDTO1.setEmail("test");
            testEmployeeDTO1.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        EmployeeDTO testEmployeeDTO2 = new EmployeeDTO();
            testEmployeeDTO2.setId(0);
            testEmployeeDTO2.setName("test");
            testEmployeeDTO2.setLastname("test");
            testEmployeeDTO2.setPytronymic("test");
            testEmployeeDTO2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.DESIGNER);
            AccountDTO testAccountDTO2 = new AccountDTO();
            testAccountDTO2.setId(0);
            testAccountDTO2.setLogin("test2");
            testAccountDTO2.setPassword("test2");
            testEmployeeDTO2.setAccount(testAccountDTO2);
            testEmployeeDTO2.setEmail("test");
            testEmployeeDTO2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);
        var actual = service.createEmployee(testEmployeeDTO1);
        //when(employeeRepository.exists(EmployeeSpecification.getSpecEquals("test", "test", "test", "test"))).thenReturn(true);

        Assertions.assertThrowsExactly(UserDuplicateException.class, ()-> {
            service.createEmployee(testEmployeeDTO2);
        });
    }

    @DisplayName("Аккаунт(логин) уже существует")
    @Test
    public void testCreateEmployeeWithExistsAccount() throws Exception {
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

        PostEmployee post = new PostEmployee();
        post.setName("MANAGER");
        StatusEmployee status = new StatusEmployee();
        status.setName("ACTIVE");

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(postEmployeeRepository.getPostEmployeeByName(anyString())).thenReturn(post);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.getStatusEmployeeByName(anyString())).thenReturn(status);
        when(employeeRepository.save(any())).thenReturn(null);
        var actual = service.createEmployee(testEmployeeDTO1);
        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(true);

        Assertions.assertThrowsExactly(AccountDuplicateException.class, () -> {
            service.createEmployee(testEmployeeDTO2);
        });
    }

    @DisplayName("Незаполненное имя(пустая строка)")
    @Test
    public void testCreateEmployeeEmptyName() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("");
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

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            service.createEmployee(testEmployeeDTO);
        });
    }

    @DisplayName("Незаполненное имя(null)")
    @Test
    public void testCreateEmployeeNullName() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName(null);
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

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            service.createEmployee(testEmployeeDTO);
        });
    }

    @DisplayName("Незаполненная фамилия(пустая строка)")
    @Test
    public void testCreateEmployeeEmptyLastname() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("");
        testEmployeeDTO.setPytronymic("test");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccountDTO = new AccountDTO();
        testAccountDTO.setId(0);
        testAccountDTO.setLogin("test");
        testAccountDTO.setPassword("test");
        testEmployeeDTO.setAccount(testAccountDTO);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            service.createEmployee(testEmployeeDTO);
        });
    }

    @DisplayName("Незаполненная фамилия(null)")
    @Test
    public void testCreateEmployeeNullLastname() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname(null);
        testEmployeeDTO.setPytronymic("test");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccountDTO = new AccountDTO();
        testAccountDTO.setId(0);
        testAccountDTO.setLogin("test");
        testAccountDTO.setPassword("test");
        testEmployeeDTO.setAccount(testAccountDTO);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            service.createEmployee(testEmployeeDTO);
        });
    }

    @DisplayName("Незаполненное отчество(пустая строка)")
    @Test
    public void testCreateEmployeeEmptyPytronymic() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("test");
        testEmployeeDTO.setPytronymic("");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccountDTO = new AccountDTO();
        testAccountDTO.setId(0);
        testAccountDTO.setLogin("test");
        testAccountDTO.setPassword("test");
        testEmployeeDTO.setAccount(testAccountDTO);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()
                -> service.createEmployee(testEmployeeDTO)
        );
    }

    @DisplayName("Незаполненное отчество(null)")
    @Test
    public void testCreateEmployeeNullPytronymic() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("test");
        testEmployeeDTO.setPytronymic(null);
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccountDTO = new AccountDTO();
        testAccountDTO.setId(0);
        testAccountDTO.setLogin("test");
        testAccountDTO.setPassword("test");
        testEmployeeDTO.setAccount(testAccountDTO);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(true);
        when(postEmployeeRepository.getPostEmployeeByName(anyString())).thenReturn(null);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(statusEmployeeRepository.getStatusEmployeeByName(anyString())).thenReturn(null);
        when(employeeRepository.save(any())).thenReturn(null);
        when(mapperEmployee.reverseMap(any())).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> service.createEmployee(testEmployeeDTO));
    }

    @DisplayName("Незаполненный логин аккаунта(пустая строка)")
    @Test
    public void testCreateEmployeeEmptyLoginAccount() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("test");
        testEmployeeDTO.setPytronymic("test");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccount = new AccountDTO();
        testAccount.setId(0);
        testAccount.setLogin("");
        testAccount.setPassword("test");
        testEmployeeDTO.setAccount(testAccount);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(false);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()
                -> service.createEmployee(testEmployeeDTO)
        );
    }

    @DisplayName("Незаполненный логин аккаунта(Null)")
    @Test
    public void testCreateEmployeeNullLoginAccount() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("test");
        testEmployeeDTO.setPytronymic("test");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccount = new AccountDTO();
        testAccount.setId(0);
        testAccount.setLogin(null);
        testAccount.setPassword("test");
        testEmployeeDTO.setAccount(testAccount);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(false);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()
                -> service.createEmployee(testEmployeeDTO)
        );
    }

    @DisplayName("Незаполненный пароль аккаунта(пустая строка)")
    @Test
    public void testCreateEmployeeEmptyPasswordAccount() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("test");
        testEmployeeDTO.setPytronymic("test");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccount = new AccountDTO();
        testAccount.setId(0);
        testAccount.setLogin("test");
        testAccount.setPassword("");
        testEmployeeDTO.setAccount(testAccount);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(false);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()
                -> service.createEmployee(testEmployeeDTO)
        );
    }

    @DisplayName("Незаполненный пароль аккаунта(null)")
    @Test
    public void testCreateEmployeeNullPasswordAccount() {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(0);
        testEmployeeDTO.setName("test");
        testEmployeeDTO.setLastname("test");
        testEmployeeDTO.setPytronymic("test");
        testEmployeeDTO.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
        AccountDTO testAccount = new AccountDTO();
        testAccount.setId(0);
        testAccount.setLogin("test");
        testAccount.setPassword(null);
        testEmployeeDTO.setAccount(testAccount);
        testEmployeeDTO.setEmail("test");
        testEmployeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(false);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(true);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()
                -> service.createEmployee(testEmployeeDTO)
        );
    }

    @DisplayName("Передача Null вместо сотрудника")
    @Test
    public void testCreateNullEmployee() {
        EmployeeDTO testEmployeeDTO = null;

        when(accountRepository.existsAccountByLogin(anyString())).thenReturn(false);
        when(postEmployeeRepository.existsPostEmployeeByName(anyString())).thenReturn(false);
        when(statusEmployeeRepository.existsStatusEmployeeByName(anyString())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(null);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()
                -> service.createEmployee(testEmployeeDTO)
        );
    }

    @DisplayName("Удаление по не существующему id")
    @Test
    public void testRemoveByNotExistsId() {
        when(employeeRepository.getReferenceById(anyInt())).thenReturn(null);
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> {
           service.removeEmployeeById(anyInt());
        });
    }

    @DisplayName("Получение сотрудника по несуществующему id")
    @Test
    public void testGetEmployeeByNotExistsId() {
        when(employeeRepository.getReferenceById(anyInt())).thenReturn(null);

        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> {
            service.getEmployeeById(anyInt());
        });
    }

    @DisplayName("Получение сотрудника по пустому логину(пустая строка) учетной записи")
    @Test
    public void testGetEmployeeByEmptyAccount() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            service.getEmployeeByAccount("");
        });
    }

    @DisplayName("Получение сотрудника по пустому логину(null) учетной записи")
    @Test
    public void testGetEmployeeByNullAccount() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            service.getEmployeeByAccount(null);
        });
    }

    @DisplayName("Получение по несуществующему логину")
    @Test
    public void testGetEmployeeByNotExistsAccount() {
        String testLogin = "UNKNOWN";

        when(employeeRepository.findEmployeeByAccount_Login(testLogin))
                .thenReturn(null);

        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> {
            service.getEmployeeByAccount(testLogin);
        });
    }

    @DisplayName("Поиск сотрудников по пустому имени/фамилии/отчеству/имейлу/логину и активному статусу")
    @Test
    public void testFindEmployeesByStringAndActive() {
        Assertions.assertAll(
                () -> Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->{
                    service.findEmployeesByString("");
                }),
                () -> Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->{
                    service.findEmployeesByString(null);
                })
        );
    }

    @DisplayName("Редактирование сотрудника с несуществующим идентификатором")
    @Test
    public void testSetEmployeeNotExistsId() {
        EmployeeDTO testEmployee = new EmployeeDTO();
            testEmployee.setId(0);
            testEmployee.setName("test1");
            testEmployee.setLastname("");
            testEmployee.setPytronymic("test1");
            testEmployee.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount = new AccountDTO();
            testAccount.setId(0);
            testAccount.setLogin("test1");
            testAccount.setPassword("test1");
            testEmployee.setAccount(testAccount);
            testEmployee.setEmail("test1");
            testEmployee.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        when(employeeRepository.existsById(anyInt())).thenReturn(false);
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> {
            service.setEmployee(anyInt(), testEmployee);
        });
    }

    @DisplayName("Редактирование сотрудника с пропущенным именем")
    @Test
    public void testSetEmployeeNullName() {
        EmployeeDTO testEmployee1 = new EmployeeDTO();
            testEmployee1.setId(0);
            testEmployee1.setName("test1");
            testEmployee1.setLastname("");
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
            testEmployee2.setLastname(null);
            testEmployee2.setPytronymic("test2");
            testEmployee2.setPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee.MANAGER);
            AccountDTO testAccount2 = new AccountDTO();
            testAccount2.setId(0);
            testAccount2.setLogin("test2");
            testAccount2.setPassword("test2");
            testEmployee2.setAccount(testAccount1);
            testEmployee2.setEmail("test2");
            testEmployee2.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()-> {
                    service.setEmployee(anyInt(), testEmployee1);
                });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()-> {
            service.setEmployee(anyInt(), testEmployee2);
        });
    }
}
