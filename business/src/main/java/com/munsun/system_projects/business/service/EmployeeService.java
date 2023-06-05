package com.munsun.system_projects.business.service;

import com.munsun.system_projects.dto.entity.EmployeeDTO;
import exp.UserNotFoundException;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws Exception;
    EmployeeDTO setEmployee(int id, EmployeeDTO newEmployeeDTO) throws Exception;
    EmployeeDTO removeEmployeeById(int id) throws UserNotFoundException;
    List<EmployeeDTO> findEmployeesByString(String str);
    EmployeeDTO getEmployeeById(int id) throws UserNotFoundException;
    EmployeeDTO getEmployeeByAccount(String login) throws UserNotFoundException;
}
