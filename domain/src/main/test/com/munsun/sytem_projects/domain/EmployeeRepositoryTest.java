package com.munsun.sytem_projects.domain;

import com.munsun.system_projects.domain.mapping.read_write_files.impl.*;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;
import com.munsun.system_projects.domain.repository.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeRepositoryTest {
    private static EmployeeRepository employeeRepository;

    @BeforeAll
    public static void setAccountRepository() {
        String pathEmployees = "../txt/employees_test.txt";
        String pathAccount = "../txt/accounts_test.txt";
        String pathStatus = "../txt/status_employee_test.txt";
        String pathPost = "../txt/post_employee_test.txt";
        employeeRepository = new EmployeeRepository(pathEmployees,
                        new EmployeeMapper(new CSVParser()),
                new PostEmployeeRepository(pathPost,
                                new PostEmployeeMapper(new CSVParser())),
                new AccountRepository(pathAccount,
                                new AccountMapper(new CSVParser())),
                new StatusEmployeeRepository(pathStatus,
                                new StatusEmployeeMapper(new CSVParser())));
    }

    @AfterEach
    public void resetEmployeeRepository() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Munir", "Sunchalyaev", null,
                new PostEmployee(3, "SEO"),
                new Account(1, "munsun", "12345qwerty"),
                "msunchalyaev@gmail.com",
                new StatusEmployee(1, "ACTIVE")));
        employees.add(new Employee(2, "Andrey", "Sunchalyaev", null,
                new PostEmployee(1, "MANAGER"),
                new Account(2, "andron1997", "588425"),
                null,
                new StatusEmployee(1, "ACTIVE")));

        employeeRepository.clear();
        employeeRepository.create(employees.get(0));
        employeeRepository.create(employees.get(1));
        employeeRepository.close();
    }

    @Test
    public void testRead() {
        Set<Employee> expected = new HashSet<>();
        expected.add(new Employee(1, "Munir", "Sunchalyaev", null,
                new PostEmployee(3, "SEO"),
                new Account(1, "munsun", "12345qwerty"),
                "msunchalyaev@gmail.com",
                new StatusEmployee(1, "ACTIVE")));
        expected.add(new Employee(2, "Andrey", "Sunchalyaev", null,
                new PostEmployee(1, "MANAGER"),
                new Account(2, "andron1997", "588425"),
                null,
                new StatusEmployee(1, "ACTIVE")));

        employeeRepository.readFile();
        var actual = employeeRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        Set<Employee> expected = new HashSet<>();
        expected.add(new Employee(1, "Munir", "Sunchalyaev", null,
                new PostEmployee(3, "SEO"),
                new Account(1, "munsun", "12345qwerty"),
                "msunchalyaev@gmail.com",
                new StatusEmployee(1, "ACTIVE")));
        expected.add(new Employee(2, "Andrey", "Sunchalyaev", null,
                new PostEmployee(1, "MANAGER"),
                new Account(2, "andron1997", "588425"),
                null,
                new StatusEmployee(1, "ACTIVE")));
        Employee e = new Employee(3, "Tolya", "Sunchalyaev", null,
                new PostEmployee(2, "DEVELOPER"),
                new Account(3, "orangeble2005", "1111"),
                null,
                new StatusEmployee(2, "REMOVED"));
        expected.add(e);

        employeeRepository.readFile();
        employeeRepository.create(e);
        employeeRepository.close();
        employeeRepository.readFile();
        var actual = employeeRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        Set<Employee> expected = new HashSet<>();
        expected.add(new Employee(1, "Munir", "Sunchalyaev", null,
                new PostEmployee(3, "SEO"),
                new Account(1, "munsun", "12345qwerty"),
                "msunchalyaev@gmail.com",
                new StatusEmployee(1, "ACTIVE")));
        expected.add(new Employee(2, "-", "--", null,
                new PostEmployee(2, "DEVELOPER"),
                new Account(3, "unknown", "588425"),
                null,
                new StatusEmployee(1, "ACTIVE")));

        employeeRepository.readFile();
        employeeRepository.update(2, new Employee(2, "-", "--", null,
                new PostEmployee(2, "DEVELOPER"),
                new Account(3, "unknown", "588425"),
                null,
                new StatusEmployee(1, "ACTIVE")));
        employeeRepository.close();
        employeeRepository.readFile();
        var actual = employeeRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDeleteById() {
        Set<Employee> expected = new HashSet<>();
        expected.add(new Employee(2, "Andrey", "Sunchalyaev", null,
                new PostEmployee(1, "MANAGER"),
                new Account(2, "andron1997", "588425"),
                null,
                new StatusEmployee(1, "ACTIVE")));

        employeeRepository.readFile();
        employeeRepository.deleteById(1);
        employeeRepository.close();
        employeeRepository.readFile();
        var actual = employeeRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }
}
