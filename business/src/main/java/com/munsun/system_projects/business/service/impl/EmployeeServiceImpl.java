package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.model.PostEmployee;
import com.munsun.system_projects.business.model.StatusEmployee;
import com.munsun.system_projects.business.repository.AccountRepository;
import com.munsun.system_projects.business.repository.PostEmployeeRepository;
import com.munsun.system_projects.business.repository.StatusEmployeeRepository;
import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Account;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.repository.EmployeeRepository;
import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.business.service.impl.specification.EmployeeSpecification;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import exp.AccountDuplicateException;
import exp.UserDuplicateException;
import exp.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PostEmployeeRepository postEmployeeRepository;
    private final StatusEmployeeRepository statusEmployeeRepository;
    private final AccountRepository accountRepository;

    private final Mapper<Employee, EmployeeDTO> mapperEmployee;
    private final Mapper<Account, AccountDTO> mapperAccount;
    private final Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> mapperPost;
    private final Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> mapperStatus;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               PostEmployeeRepository postEmployeeRepository,
                               StatusEmployeeRepository statusEmployeeRepository,
                               AccountRepository accountRepository,
                               Mapper<Employee, EmployeeDTO> mapperEmployee,
                               Mapper<Account, AccountDTO> mapperAccount,
                               Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> mapperPost,
                               Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> mapperStatus)
    {
        this.employeeRepository = employeeRepository;
        this.postEmployeeRepository = postEmployeeRepository;
        this.statusEmployeeRepository = statusEmployeeRepository;
        this.accountRepository = accountRepository;
        this.mapperEmployee = mapperEmployee;
        this.mapperAccount = mapperAccount;
        this.mapperPost = mapperPost;
        this.mapperStatus = mapperStatus;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws Exception {
        log.debug("Создание нового сотрудника...");
        checkEmployeeDTO(employeeDTO);
        employeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);
        Employee employee = mapperEmployee.map(employeeDTO);
        employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
        employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(employee.getStatusEmployee().getName()));
        log.info("Выполнение сохранения сотрудника...");
        employeeRepository.save(employee);
        log.info("Сохранение прошло успешно...");
        return mapperEmployee.reverseMap(employee);
    }

    private void checkEmployeeDTO(EmployeeDTO employeeDTO) throws Exception {
        log.debug("Проверка полей сотрудника на заполненность...");
        if(employeeDTO==null
                || employeeDTO.getName()==null || "".equals(employeeDTO.getName())
                || employeeDTO.getLastname()==null || "".equals(employeeDTO.getLastname())
                || employeeDTO.getAccount().getLogin()==null || employeeDTO.getAccount().getPassword()==null
                || "".equals(employeeDTO.getAccount().getLogin()) || "".equals(employeeDTO.getAccount().getPassword())
                || "".equals(employeeDTO.getPytronymic())
                || "".equals(employeeDTO.getEmail()))
        {
            String logMessage;
            if(employeeDTO==null)
                logMessage = "employee=null";
            else if(employeeDTO.getName()==null || "".equals(employeeDTO.getName()))
                logMessage = "name=empty/null";
            else if(employeeDTO.getLastname()==null || "".equals(employeeDTO.getLastname()))
                logMessage = "lastname=empty/null";
            else if(employeeDTO.getAccount().getLogin()==null || "".equals(employeeDTO.getAccount().getLogin()))
                logMessage = "login=empty/null";
            else if(employeeDTO.getAccount().getPassword()==null || "".equals(employeeDTO.getAccount().getPassword()))
                logMessage = "password=empty/null";
            else if("".equals(employeeDTO.getPytronymic()))
                logMessage = "pytronymic=empty";
            else
                logMessage = "email";

            log.error("Проверка полей сотрудника на заполненность провалено: " + logMessage);
            throw new IllegalArgumentException("Обязательные поля не заполнены");
        }
        log.info("Поиск дубликатов сотрудников...");
        if(employeeRepository.exists(EmployeeSpecification.getSpecEquals(employeeDTO.getName(), employeeDTO.getLastname(),
                employeeDTO.getPytronymic(), employeeDTO.getEmail())))
        {
            log.error("Поиск дубликатов сотрудников провален...");
            throw new UserDuplicateException();
        }
        log.info("Поиск дубликатов логинов...");
        if(accountRepository.existsAccountByLogin(employeeDTO.getAccount().getLogin())) {
            log.error("Поиск дубликатов логинов провален: login=" + employeeDTO.getAccount().getLogin());
            throw new AccountDuplicateException();
        }
        log.debug("Проверка полей сотрудника выполнена успешно...");
    }

    @Override
    public EmployeeDTO setEmployee(int id, EmployeeDTO newEmployeeDTO) throws Exception {
        log.debug("Редактирование сотрудника..");
        log.info("Поиск сотрудника по идентификатору...");
        if(!employeeRepository.existsById(id)) {
            log.error("Поиск сотрудника по идентификатору провален: id=" + id);
            throw new UserNotFoundException();
        }
        checkEmployeeDTO(newEmployeeDTO);
        log.info("Получение сотрудника по идентификатору...");
        Employee employee = employeeRepository.getReferenceById(id);
        log.info("Получение сотрудника по идентификатору выполнено...");
        log.info("Проверка статуса ACTIVE сотрудника...");
        if(!employee.getStatusEmployee().getName()
                .equals(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name()))
        {
            log.error("Проверка статуса ACTIVE сотрудника провалено: status=" + employee.getStatusEmployee().getName());
            throw new IllegalArgumentException("Статус должен быть ACTIVE");
        }
        employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
        employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(employee.getStatusEmployee().getName()));
        log.info("Выполнение редактирования сотрудника по идентификатору...");
        employeeRepository.setEmployee(id, employee);
        log.info("Выполнение редактирования сотрудника по идентификатору прошло успешно...");
        return mapperEmployee.reverseMap(employeeRepository.getReferenceById(id));
    }

    @Override
    public EmployeeDTO removeEmployeeById(int id) throws UserNotFoundException {
        log.debug("Выполнение редактирования сотрудника по идентификатору...");
        log.info("Поиск сотрудника по идентификатору...");
        boolean isExists = employeeRepository.existsById(id);
        if(!isExists) {
            log.error("Поиск сотрудника по идентификатору провален: id=" + id);
            throw new UserNotFoundException();
        }
        var statusRemoved = statusEmployeeRepository.getStatusEmployeeByName(com.munsun.system_projects.commons.enums.StatusEmployee.REMOVED.name());
        log.info("Редактирование сотрудника...");
        employeeRepository.setEmployeeStatus(id, statusRemoved);
        log.info("Редактирование сотрудника успешно...");
        return mapperEmployee.reverseMap(employeeRepository.getReferenceById(id));
    }

    @Override
    public List<EmployeeDTO> findEmployeesByString(String str) {
        log.debug("Получение сотрудников по строке...");
        log.info("Проверка входной строки...");
        if(str==null || "".equals(str)) {
            log.error("Проверка входной строки провалено: string=empty/null");
            throw new IllegalArgumentException("Поле не заполнено");
        }
        log.info("Проверка входной строки выполнена успешно...");
        List<Employee> employees = employeeRepository.search(str, com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name());
        log.debug("Получение сотрудников по строке выполнено успешно...");
        return employees.stream()
                .map(mapperEmployee::reverseMap)
                .toList();
    }

    @Transactional
    @Override
    public EmployeeDTO getEmployeeById(int id) throws UserNotFoundException {
        log.debug("Получение сотрудника по идентификатору...");
        Employee employee = employeeRepository.getReferenceById(id);
        if(employee == null) {
            log.error("Получение сотрудника по идентификатору провалено: id=" + id);
            throw new UserNotFoundException();
        }
        return mapperEmployee.reverseMap(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByAccount(String login) throws IllegalArgumentException, UserNotFoundException {
        log.debug("Получение сотрудника по учетной записи...");
        log.info("Проверка входной строки...");
        if("".equals(login) || login==null)
            throw new IllegalArgumentException("Логин не заполнен: login=empty/null");
        Employee employee = employeeRepository.findEmployeeByAccount_Login(login)
                .orElseThrow(UserNotFoundException::new);
        return mapperEmployee.reverseMap(employee);
    }
}