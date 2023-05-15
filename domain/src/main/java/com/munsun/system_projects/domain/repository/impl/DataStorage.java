package com.munsun.system_projects.domain.repository.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.model.Employee;

public class DataStorage extends EmployeeRepository {
    public DataStorage(String path,
                       Mapper<Employee> mapper,
                       PostEmployeeRepository postEmployeeRepository,
                       AccountRepository accountRepository,
                       StatusEmployeeRepository statusEmployeeRepository)
    {
        super(path, mapper, postEmployeeRepository, accountRepository, statusEmployeeRepository);
    }
}
