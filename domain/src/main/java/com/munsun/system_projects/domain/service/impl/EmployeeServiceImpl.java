package com.munsun.system_projects.domain.service.impl;

import com.munsun.system_projects.commons.enums.StatusEmployee;
import com.munsun.system_projects.domain.mapping.Mapper;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Command;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.model.Project;
import com.munsun.system_projects.domain.repository.EmployeeRepository;
import com.munsun.system_projects.domain.service.EmployeeService;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import com.munsun.system_projects.dto.entity.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final Mapper<Employee, EmployeeDTO> mapperEmployee;
    private final Mapper<Account, AccountDTO> mapperAccount;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository,
                               Mapper<Employee, EmployeeDTO> mapperEmployee,
                               Mapper<Account, AccountDTO> mapperAccount)
    {
        this.repository = repository;
        this.mapperEmployee = mapperEmployee;
        this.mapperAccount = mapperAccount;
    }

    @Override
    public void createEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setStatusEmployee(StatusEmployee.ACTIVE);
        Employee employee = mapperEmployee.map(employeeDTO);
        repository.save(employee);
    }

    @Override
    public void setEmployee(int id, EmployeeDTO newEmployeeDTO) {
        if(newEmployeeDTO.getStatusEmployee() == StatusEmployee.REMOVED)
            throw new IllegalArgumentException();
        repository.deleteById(id);
        Employee employee = mapperEmployee.map(newEmployeeDTO);
        repository.save(employee);
    }

    @Transactional
    @Override
    public void removeEmployeeById(int id) {
        Employee employee = repository.getReferenceById(id);
        repository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> getEmployees(String str) {
        List<Employee> employees = repository.search(str, StatusEmployee.ACTIVE.name());
        return employees.stream()
                .map(mapperEmployee::reverseMap)
                .toList();
    }

    @Transactional
    @Override
    public EmployeeDTO getEmployee(int id) {
        Employee employee = repository.getReferenceById(id);
        return mapperEmployee.reverseMap(employee);
    }

    @Override
    public EmployeeDTO getEmployee(AccountDTO accountDTO) {
        Employee employee = repository.get(accountDTO.getLogin(), accountDTO.getPassword());
        return mapperEmployee.reverseMap(employee);
    }
}