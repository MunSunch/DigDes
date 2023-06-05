package com.munsun.system_projects.business.tests.service;

import com.munsun.system_projects.dto.entity.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO setEmployee(int id, EmployeeDTO newEmployeeDTO);
    EmployeeDTO removeEmployeeById(int id);
    List<EmployeeDTO> findEmployeesByString(String str);
    EmployeeDTO getEmployee(int id);
    EmployeeDTO getEmployeeByAccount(String login);
}
