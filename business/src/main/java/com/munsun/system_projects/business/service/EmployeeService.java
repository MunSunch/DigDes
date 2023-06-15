package com.munsun.system_projects.business.service;

import com.munsun.system_projects.commons.exceptions.AccountEmptyFieldsException;
import com.munsun.system_projects.commons.exceptions.EmployeeEmptyFieldsException;
import com.munsun.system_projects.commons.exceptions.EmployeeNotFoundException;
import com.munsun.system_projects.dto.entity.in.AccountDtoIn;
import com.munsun.system_projects.dto.entity.in.EmployeeDtoIn;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;

import java.util.List;

public interface EmployeeService {
    EmployeeDtoOut createEmployee(EmployeeDtoIn employeeDtoIn) throws Exception;
    EmployeeDtoOut setEmployee(int id, EmployeeDtoIn employeeDtoIn) throws Exception;
    EmployeeDtoOut removeEmployeeById(int id) throws EmployeeNotFoundException;
    List<EmployeeDtoOut> findEmployeesByString(String str);
    EmployeeDtoOut getEmployeeById(int id) throws EmployeeNotFoundException;
    EmployeeDtoOut getEmployeeByAccount(AccountDtoIn accountDtoIn) throws EmployeeNotFoundException, AccountEmptyFieldsException;
    List<EmployeeDtoOut> getAllEmployees();
}
