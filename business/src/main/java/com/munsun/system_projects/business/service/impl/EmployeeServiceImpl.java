package com.munsun.system_projects.business.tests.service.impl;

import com.munsun.system_projects.business.tests.model.PostEmployee;
import com.munsun.system_projects.business.tests.model.StatusEmployee;
import com.munsun.system_projects.business.tests.repository.AccountRepository;
import com.munsun.system_projects.business.tests.repository.PostEmployeeRepository;
import com.munsun.system_projects.business.tests.repository.StatusEmployeeRepository;
import com.munsun.system_projects.business.tests.mapping.Mapper;
import com.munsun.system_projects.business.tests.model.Account;
import com.munsun.system_projects.business.tests.model.Employee;
import com.munsun.system_projects.business.tests.repository.EmployeeRepository;
import com.munsun.system_projects.business.tests.service.EmployeeService;
import com.munsun.system_projects.business.tests.service.impl.specification.EmployeeSpecification;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final PostEmployeeRepository postEmployeeRepository;
    private final StatusEmployeeRepository statusEmployeeRepository;
    private final AccountRepository accountRepository;

    private final Mapper<Employee, EmployeeDTO> mapperEmployee;
    private final Mapper<Account, AccountDTO> mapperAccount;
    private final Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> mapperPost;
    private final Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> mapperStatus;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PostEmployeeRepository postEmployeeRepository, StatusEmployeeRepository statusEmployeeRepository, AccountRepository accountRepository, Mapper<Employee, EmployeeDTO> mapperEmployee, Mapper<Account, AccountDTO> mapperAccount, Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> mapperPost, Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> mapperStatus) {
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
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        logger.info("Создание нового сотрудника...");
        checkEmployeeDTO(employeeDTO);
        employeeDTO.setStatusEmployee(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE);
        Employee employee = mapperEmployee.map(employeeDTO);
        employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
        employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(employee.getStatusEmployee().getName()));
        logger.info("Сохранение сотрудника...");
        employeeRepository.save(employee);
        logger.info("Сохранение прошло успешно...");
        return mapperEmployee.reverseMap(employee);
    }

    private void checkEmployeeDTO(EmployeeDTO employeeDTO) {
        logger.info("Проверка полей сотрудника на заполненность...");
        if(employeeDTO==null
                || employeeDTO.getName()==null || "".equals(employeeDTO.getName())
                || employeeDTO.getLastname()==null || "".equals(employeeDTO.getLastname())
                || employeeDTO.getAccount().getLogin()==null || employeeDTO.getAccount().getPassword()==null
                || "".equals(employeeDTO.getAccount().getLogin()) || "".equals(employeeDTO.getAccount().getPassword())
                || "".equals(employeeDTO.getPytronymic())
                || "".equals(employeeDTO.getEmail()))
        {
            logger.error("Проверка на заполненность сотрудника провалена...");
            throw new IllegalArgumentException("Обязательные поля не заполнены");
        }
        logger.info("Поиск дубликатов сотрудников...");
        if(employeeRepository.exists(EmployeeSpecification.getSpecEquals(employeeDTO.getName(), employeeDTO.getLastname(),
                employeeDTO.getPytronymic(), employeeDTO.getEmail())))
        {
            logger.error("Поиск дубликатов сотрудников провален...");
            throw new IllegalArgumentException("Сотрудник с таким ФИО и имейлом существует");
        }
        logger.info("Поиск дубликатов логинов...");
        if(accountRepository.existsAccountByLogin(employeeDTO.getAccount().getLogin())) {
            logger.error("Поиск дубликатов логинов провален...");
            throw new IllegalArgumentException("Такая учетная запись существует");
        }
        logger.info("Проверка полей сотрудника выполнена...");
    }

    @Override
    public EmployeeDTO setEmployee(int id, EmployeeDTO newEmployeeDTO) {
        if(!employeeRepository.existsById(id))
            throw new IllegalArgumentException("Сотрудника с таким идентификатором не существует");
        checkEmployeeDTO(newEmployeeDTO);
        Employee employee = employeeRepository.getReferenceById(id);
        if(!employee.getStatusEmployee().getName()
                .equals(com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name()))
            throw new IllegalArgumentException("Статус должен быть ACTIVE");
        employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
        employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(employee.getStatusEmployee().getName()));
        employeeRepository.setEmployee(id, employee);
        return mapperEmployee.reverseMap(employeeRepository.getReferenceById(id));
    }

    @Override
    public EmployeeDTO removeEmployeeById(int id) {
        boolean isExists = employeeRepository.existsById(id);
        if(!isExists)
            throw new IllegalArgumentException("Сотрудника с таким идентификатором не существует");
        var statusRemoved = statusEmployeeRepository.getStatusEmployeeByName(com.munsun.system_projects.commons.enums.StatusEmployee.REMOVED.name());
        employeeRepository.setEmployeeStatus(id, statusRemoved);
        return mapperEmployee.reverseMap(employeeRepository.getReferenceById(id));
    }

    @Override
    public List<EmployeeDTO> findEmployeesByString(String str) {
        if(str==null || "".equals(str))
            throw new IllegalArgumentException("Поле не заполнено");
        List<Employee> employees = employeeRepository.search(str, com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE.name());
        return employees.stream()
                .map(mapperEmployee::reverseMap)
                .toList();
    }

    @Transactional
    @Override
    public EmployeeDTO getEmployee(int id) {
        Employee employee = employeeRepository.getReferenceById(id);
        if(employee == null)
            throw new IllegalArgumentException("Сотрудника с таким идентификатором не существует");
        return mapperEmployee.reverseMap(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByAccount(String login) throws IllegalArgumentException {
        if("".equals(login) || login==null)
            throw new IllegalArgumentException("Логин не заполнен");
        Employee employee = employeeRepository.findEmployeeByAccount_Login(login).orElseThrow(
                () -> new IllegalArgumentException("Сотрудника с такой учетной записью не существует")
        );
        return mapperEmployee.reverseMap(employee);
    }
}