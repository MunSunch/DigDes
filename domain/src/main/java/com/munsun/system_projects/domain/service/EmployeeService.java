package com.munsun.system_projects.domain.service;

import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    void createEmployee(EmployeeDTO employeeDTO);
    void setEmployee(int id, EmployeeDTO newEmployeeDTO);
    void removeEmployeeById(int id);
    List<EmployeeDTO> getEmployees(String str);
    EmployeeDTO getEmployee(int id);
    EmployeeDTO getEmployee(AccountDTO accountDTO);
}
