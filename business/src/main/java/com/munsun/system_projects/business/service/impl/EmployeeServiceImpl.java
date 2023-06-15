package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.model.Account;
import com.munsun.system_projects.business.model.PostEmployee;
import com.munsun.system_projects.business.repository.AccountRepository;
import com.munsun.system_projects.business.repository.PostEmployeeRepository;
import com.munsun.system_projects.business.repository.StatusEmployeeRepository;
import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.repository.EmployeeRepository;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.business.service.impl.specification.EmployeeSpecification;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.AccountDtoIn;
import com.munsun.system_projects.dto.entity.in.EmployeeDtoIn;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PostEmployeeRepository postEmployeeRepository;
    private final StatusEmployeeRepository statusEmployeeRepository;
    private final AccountRepository accountRepository;

    private final Mapper<Employee, EmployeeDtoOut> mapperEmployee;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               PostEmployeeRepository postEmployeeRepository,
                               StatusEmployeeRepository statusEmployeeRepository,
                               AccountRepository accountRepository,
                               Mapper<Employee, EmployeeDtoOut> mapperEmployee)
    {
        this.employeeRepository = employeeRepository;
        this.postEmployeeRepository = postEmployeeRepository;
        this.statusEmployeeRepository = statusEmployeeRepository;
        this.accountRepository = accountRepository;
        this.mapperEmployee = mapperEmployee;
    }

    @Override
    public EmployeeDtoOut createEmployee(EmployeeDtoIn employeeDtoIn) throws EmployeeDuplicateException, AccountDuplicateException, EmployeeEmptyFieldsException {
        log.debug("Создание нового сотрудника...");
            checkEmptyFieldsEmployee(employeeDtoIn);
            checkDuplicateEmployee(employeeDtoIn);
            checkDuplicateAccount(employeeDtoIn.getAccount());

            Employee employee = new Employee();
                employee.setId(0);
                employee.setName(employeeDtoIn.getName());
                employee.setLastname(employeeDtoIn.getLastname());
                employee.setPatronymic(employeeDtoIn.getPytronymic());
                Account account = new Account();
                    account.setId(0);
                    account.setLogin(employeeDtoIn.getAccount().getLogin());
                    account.setPassword(new BCryptPasswordEncoder().encode(employeeDtoIn.getAccount().getPassword()));
                employee.setAccount(account);
                PostEmployee post = postEmployeeRepository.getPostEmployeeByName(employeeDtoIn.getPostEmployee().name());
                employee.setPostEmployee(post);
                employee.setEmail(employeeDtoIn.getEmail());
                employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name()));
            var result = employeeRepository.save(employee);
        log.debug("Сохранение прошло успешно...");
            return mapperEmployee.reverseMap(result);
    }

    private void checkEmptyFieldsEmployee(EmployeeDtoIn employeeDtoIn) throws EmployeeEmptyFieldsException {
        log.info("Проверка заполнения обязательных полей сотрудника: "+employeeDtoIn);
            checkEmptyOrNullStringEmployee(employeeDtoIn.getName(), "name");
            checkEmptyOrNullStringEmployee(employeeDtoIn.getLastname(), "lastname");
            checkEmptyString(employeeDtoIn.getPytronymic(), "pytronymic");
            checkEmptyOrNullStringEmployee(employeeDtoIn.getAccount().getLogin(), "login");
            checkEmptyOrNullStringEmployee(employeeDtoIn.getAccount().getPassword(), "password");
            checkEmptyString(employeeDtoIn.getEmail(), "email");
            checkNullPostEmployee(employeeDtoIn.getPostEmployee());
        log.info("Проверка заполнения обязательных полей сотрудника успешно: "+employeeDtoIn);
    }

    private void checkEmptyOrNullStringEmployee(String str, String message) throws EmployeeEmptyFieldsException {
        log.info("Проверка строки: "+message+"="+str);
            if(str==null || "".equals(str)) {
                log.error("Проверка строки провалено: "+message+"="+str);
                throw new EmployeeEmptyFieldsException();
            }
        log.info("Проверка строки выполнена успешно: "+message+"="+str);
    }

    private void checkEmptyString(String str, String message) throws EmployeeEmptyFieldsException {
        log.info("Проверка строки: "+message+"="+str);
            if("".equals(str)) {
                log.error("Проверка строки провалено: "+message+"="+str);
                throw new EmployeeEmptyFieldsException();
            }
        log.info("Проверка строки выполнена успешно: "+message+"="+str);
    }

    private void checkDuplicateEmployee(EmployeeDtoIn employeeDtoIn) throws EmployeeDuplicateException {
        log.info("Поиск дубликатов сотрудников: "+employeeDtoIn);
            if(employeeRepository.exists(EmployeeSpecification.equals(employeeDtoIn.getName(), employeeDtoIn.getLastname(),
                    employeeDtoIn.getPytronymic(), employeeDtoIn.getEmail())))
            {
                log.error("Поиск дубликатов сотрудников провален...");
                throw new EmployeeDuplicateException();
            }
        log.info("Поиск дубликатов сотрудников успешно: "+employeeDtoIn);
    }

    private void checkDuplicateAccount(AccountDtoIn account) throws AccountDuplicateException {
        log.info("Проверка дубликатов аккаунтов: "+account);
            if(accountRepository.existsAccountByLogin(account.getLogin())) {
                log.error("Проверка дубликатов аккаунтов провалено: account="+account);
                throw new AccountDuplicateException();
            }
        log.info("Проверка дубликатов аккаунтов успешно: "+account);
    }

    private void checkNullPostEmployee(com.munsun.system_projects.commons.enums.PostEmployee postEmployee) throws EmployeeEmptyFieldsException {
        log.info("Проверка должности на NULL...");
            if(postEmployee == null) {
                log.error("Проверка должности на NULL провалено: post="+postEmployee);
                    throw new EmployeeEmptyFieldsException();
            }
        log.info("Проверка должности на NULL успешно");
    }

    @Override
    public EmployeeDtoOut setEmployee(int id, EmployeeDtoIn employeeDtoIn) throws EmployeeNotFoundException, EmployeeEmptyFieldsException, EmployeeIncorrectStatusException {
        log.debug("Редактирование сотрудника по идентификатору: id="+id+", emp="+employeeDtoIn);
            checkIdEmployee(id);
            checkEmptyFieldsEmployee(employeeDtoIn);

            Employee employee = employeeRepository.getReferenceById(id);
            checkStatusEmployee(employee);
                employee.setName(employeeDtoIn.getName());
                employee.setLastname(employeeDtoIn.getLastname());
                employee.setPatronymic(employeeDtoIn.getPytronymic());
                Account account = accountRepository.getReferenceById(employee.getAccount().getId());
                    account.setLogin(employeeDtoIn.getAccount().getLogin());
                    account.setPassword(new BCryptPasswordEncoder().encode(employeeDtoIn.getAccount().getPassword()));
                employee.setAccount(account);
                employee.setEmail(employeeDtoIn.getEmail());
                employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
            var result = employeeRepository.save(employee);
        log.debug("Редактирование сотрудника по идентификатору успешно: "+id+", emp="+result);
            return mapperEmployee.reverseMap(result);
    }

    private void checkIdEmployee(int id) throws EmployeeNotFoundException {
        log.info("Проверка идентификатора сотрудника: "+id);
            if(!employeeRepository.existsById(id)) {
                log.error("Проверка идентификатора сотрудника провален: id=" + id);
                throw new EmployeeNotFoundException();
            }
        log.info("Проверка идентификатора сотрудника успешно: "+id);
    }

    private void checkStatusEmployee(Employee employee) throws EmployeeIncorrectStatusException {
        log.info("Проверка статуса ACTIVE сотрудника: "+employee);
            if(!employee.getStatusEmployee().getName()
                    .equals(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name()))
            {
                log.error("Проверка статуса ACTIVE сотрудника провалено: status=" + employee.getStatusEmployee().getName());
                throw new EmployeeIncorrectStatusException();
            }
        log.info("Проверка статуса ACTIVE сотрудника успешно: "+employee);
    }

    @Override
    public EmployeeDtoOut removeEmployeeById(int id) throws EmployeeNotFoundException {
        log.debug("Выполнение редактирования сотрудника по идентификатору: "+id);
            checkIdEmployee(id);
            Employee employee = employeeRepository.getReferenceById(id);
            employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(com.munsun.system_projects.commons.enums.StatusEmployee.REMOVED.name()));
            var result = employeeRepository.save(employee);
        log.debug("Выполнение редактирования сотрудника по идентификатору: id");
            return mapperEmployee.reverseMap(result);
    }

    @Override
    public List<EmployeeDtoOut> findEmployeesByString(String str) {
        log.debug("Получение сотрудников по строке...");
            List<Employee> employees = employeeRepository.search(str, com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name());
        log.debug("Получение сотрудников по строке выполнено успешно...");
        return employees.stream()
                .map(mapperEmployee::reverseMap)
                .toList();
    }

    @Transactional
    @Override
    public EmployeeDtoOut getEmployeeById(int id) throws EmployeeNotFoundException {
        log.debug("Получение сотрудника по идентификатору: "+id);
            checkIdEmployee(id);
            Employee employee = employeeRepository.getReferenceById(id);
        log.debug("Получение сотрудника по идентификатору успешно: "+id);
            return mapperEmployee.reverseMap(employee);
    }

    @Override
    public EmployeeDtoOut getEmployeeByAccount(AccountDtoIn accountDtoIn) throws EmployeeNotFoundException, AccountEmptyFieldsException {
        log.debug("Получение сотрудника по учетной записи: login="+accountDtoIn.getLogin());
            checkEmptyOrNullStringAccount(accountDtoIn.getLogin(), "login");
            Employee employee = employeeRepository.findEmployeeByAccount_Login(accountDtoIn.getLogin())
                                                  .orElseThrow(EmployeeNotFoundException::new);
        log.debug("Получение сотрудника по учетной записи: login="+accountDtoIn.getLogin());
            return mapperEmployee.reverseMap(employee);
    }

    private void checkEmptyOrNullStringAccount(String login, String original) throws AccountEmptyFieldsException {
        log.info("Проверка строки: "+original+"="+login);
        if(login==null || "".equals(login)) {
            log.error("Проверка строки провалено: "+original+"="+login);
            throw new AccountEmptyFieldsException();
        }
        log.info("Проверка строки выполнена успешно: "+original+"="+login);
    }

    @Override
    public List<EmployeeDtoOut> getAllEmployees() {
        log.debug("Получение всех сотрудников...");
            var employees = employeeRepository.findAll();
        log.debug("Получение всех сотрудников успешно");
            return employees.stream()
                    .map(mapperEmployee::reverseMap)
                    .collect(Collectors.toList());
    }
}