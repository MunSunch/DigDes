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
import com.munsun.system_projects.business.service.impl.specification.AccountSpecification;
import com.munsun.system_projects.business.service.impl.specification.EmployeeSpecification;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Employee employee = mapperEmployee.map(employeeDTO);
        checkEmployee(employee);
        employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
        employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(employee.getStatusEmployee().getName()));
        employeeRepository.save(employee);
        return mapperEmployee.reverseMap(employee);
    }

    private void checkEmployee(Employee employee) {
        if(checkDuplicatesAccount(employee.getAccount()))
            throw new IllegalArgumentException("Такая учетная запись существует");
        if(!checkExistsPostEmployee(employee.getPostEmployee()))
            throw new IllegalArgumentException("Такой должности не существует");
        if(!checkExistsStatusEmployee(employee.getStatusEmployee()))
            throw new IllegalArgumentException("Такого статуса не существует");
        if(employeeRepository.exists(EmployeeSpecification.getSpecEquals(employee)))
            throw new IllegalArgumentException("Сотрудник с таким ФИО и имейлом существует");
    }

    private boolean checkDuplicatesAccount(Account account) {
        return accountRepository.exists(AccountSpecification.getSpecEquals(account));
    }

    private boolean checkExistsPostEmployee(PostEmployee post) {
        return postEmployeeRepository.getPostEmployeeByName(post.getName()) != null;
    }

    private boolean checkExistsStatusEmployee(StatusEmployee status) {
        return statusEmployeeRepository.getStatusEmployeeByName(status.getName()) != null;
    }

    @Override
    public EmployeeDTO setEmployee(int id, EmployeeDTO newEmployeeDTO) {
        if(newEmployeeDTO.getStatusEmployee() != com.munsun.system_projects.commons.enums.StatusEmployee.ACTIVE)
            throw new IllegalArgumentException("Статус должен быть ACTIVE");
        if(!employeeRepository.existsById(id))
            throw new IllegalArgumentException("Сотрудника с таким идентификатором не существует");
        Employee employee = mapperEmployee.map(newEmployeeDTO);
        checkEmployee(employee);
        employee.setPostEmployee(postEmployeeRepository.getPostEmployeeByName(employee.getPostEmployee().getName()));
        employee.setStatusEmployee(statusEmployeeRepository.getStatusEmployeeByName(employee.getStatusEmployee().getName()));
        Employee result = employeeRepository.getReferenceById(id);
        return mapperEmployee.reverseMap(result);
    }

    @Transactional
    @Override
    public EmployeeDTO removeEmployeeById(int id) {
        Employee employee = employeeRepository.getReferenceById(id);
        if(employee == null)
            throw new IllegalArgumentException("Сотрудника с таким идентификатором не существует");
        StatusEmployee removedStatus = statusEmployeeRepository.getStatusEmployeeByName(com.munsun.system_projects.commons.enums.StatusEmployee.REMOVED.name());
        employee.setStatusEmployee(removedStatus);
        EmployeeDTO employeeDTO = mapperEmployee.reverseMap(employee);
        setEmployee(employee.getId(), employeeDTO);
        return mapperEmployee.reverseMap(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployees(String str) {
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
    public EmployeeDTO getEmployee(AccountDTO accountDTO) {
        Account account = mapperAccount.map(accountDTO);
        Employee employee = employeeRepository.get(account);
        if(employee == null)
            throw new IllegalArgumentException("Сотрудника с такой учетной записью не существует");
        return mapperEmployee.reverseMap(employee);
    }
}