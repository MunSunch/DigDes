package com.munsun.system_projects.security.service;

import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSecurityServiceImpl implements UserDetailsService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeSecurityServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Employee employee = repository.findEmployeeByAccount_Login(login).orElseThrow(()->
                new UsernameNotFoundException("Сотрудника с таким логином/паролем не существует")
        );
        return EmployeeSecurity.mapUser(employee);
    }
}
