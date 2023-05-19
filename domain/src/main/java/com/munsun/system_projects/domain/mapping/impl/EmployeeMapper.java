package com.munsun.system_projects.domain.mapping.impl;

import com.munsun.system_projects.domain.mapping.Mapper;
import com.munsun.system_projects.domain.mapping.Parser;
import com.munsun.system_projects.domain.model.Employee;

public class EmployeeMapper extends Mapper<Employee> {
    public EmployeeMapper(Parser parser) {
        super(parser);
    }

    @Override
    public Employee mapObject(String line) {
        String[] values = parser.parse(line);
        Employee employee = new Employee();
            employee.setId(Integer.parseInt(values[0]));
            employee.setName(values[1]);
            employee.setLastname(values[2]);
            employee.setPytronymic(values[3]);
            employee.setPostEmployee(null);
            employee.setAccount(null);
            employee.setEmail(values[6]);
            employee.setStatusEmployee(null);
        return employee;
    }

    @Override
    public String mapString(Employee employee) {
        return employee.getId() + ","
                + employee.getName() + ","
                + employee.getPytronymic() + ","
                + null + ","
                + null + ","
                + employee.getEmail() + ","
                + null;
    }
}
