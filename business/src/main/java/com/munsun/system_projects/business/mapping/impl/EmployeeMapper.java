package com.munsun.system_projects.business.mapping.mapping.impl;

import com.munsun.system_projects.business.mapping.mapping.Mapper;
import com.munsun.system_projects.business.model.Account;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.PostEmployee;
import com.munsun.system_projects.business.model.StatusEmployee;
import com.munsun.system_projects.dto.entity.AccountDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements Mapper<Employee, EmployeeDTO> {
    private Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> mapperPost;
    private Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> mapperStatusEmployee;
    private Mapper<Account, AccountDTO> mapperAccount;

    @Autowired
    public EmployeeMapper(Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> mapperPost,
                          Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> mapperStatusEmployee,
                          Mapper<Account, AccountDTO> mapperAccount)
    {
        this.mapperPost = mapperPost;
        this.mapperStatusEmployee = mapperStatusEmployee;
        this.mapperAccount = mapperAccount;
    }

    @Override
    public Employee map(EmployeeDTO obj) {
        Employee employee = new Employee();
        employee.setId(obj.getId());
        employee.setName(obj.getName());
        employee.setLastname(obj.getLastname());
        employee.setPatronymic(obj.getPytronymic());
        employee.setPostEmployee(mapperPost.map(obj.getPostEmployee()));
        employee.setAccount(mapperAccount.map(obj.getAccount()));
        employee.setEmail(obj.getEmail());
        employee.setStatusEmployee(mapperStatusEmployee.map(obj.getStatusEmployee()));
        return employee;
    }

    @Override
    public EmployeeDTO reverseMap(Employee obj) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(obj.getId());
        employeeDTO.setName(obj.getName());
        employeeDTO.setLastname(obj.getLastname());
        employeeDTO.setPytronymic(obj.getPatronymic());
        employeeDTO.setPostEmployee(mapperPost.reverseMap(obj.getPostEmployee()));
        employeeDTO.setAccount(mapperAccount.reverseMap(obj.getAccount()));
        employeeDTO.setEmail(obj.getEmail());
        employeeDTO.setStatusEmployee(mapperStatusEmployee.reverseMap(obj.getStatusEmployee()));
        return employeeDTO;
    }
}
