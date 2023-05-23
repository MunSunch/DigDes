package com.munsun.system_projects.domain.service;

import com.munsun.system_projects.commons.enums.StatusEmployee;
import com.munsun.system_projects.domain.mapping.Mapper;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.repository.EmployeeRepository;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private Mapper<Employee, EmployeeDTO> mapper;

    @Autowired
    public EmployeeService(EmployeeRepository repository, Mapper<Employee, EmployeeDTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setStatusEmployee(StatusEmployee.ACTIVE);
        Employee employee = mapper.map(employeeDTO);
        repository.save(employee);
    }
}










