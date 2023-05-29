package com.munsun.system_projects.business.service;

import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO setEmployee(int id, EmployeeDTO newEmployeeDTO);
    EmployeeDTO removeEmployeeById(int id);
    List<EmployeeDTO> getEmployees(String str);
    EmployeeDTO getEmployee(int id);
    EmployeeDTO getEmployee(String login);
}
