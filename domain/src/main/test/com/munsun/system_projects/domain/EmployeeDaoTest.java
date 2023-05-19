package com.munsun.system_projects.domain;

import com.munsun.system_projects.domain.dao.impl.EmployeeDAO;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EmployeeDaoTest {
    private static EmployeeDAO employeeDAO;

    @BeforeAll
    public static void setEmployeeDAO() {
        employeeDAO = new EmployeeDAO("jdbc:postgresql://localhost:5432/system_projects_db",
                "postgres", "4669");
    }

    @Test
    public void testGetById() {
        Employee expected = new Employee(5, "Munir", "Sunchalyaev", null,
                new PostEmployee(3, "developer"),
                new Account(1, "munsun", "12345qwerty"),
                "msunchalyaev@gmail.com",
                new StatusEmployee(1, "active"));

        var actual = employeeDAO.getById(5);

        Assertions.assertEquals(expected, actual);
    }

    @Disabled
    @Test
    public void testGetAll() {
        var actual = employeeDAO.getAll();
    }

    @Test
    public void testSave() {
        int expected = 1;
        Employee testEmployee = new Employee("test", "test", null,
                new PostEmployee(3, "developer"),
                new Account(3,"kakadu","kaka321"),
                null,
                new StatusEmployee(1, "active"));

        var actual = employeeDAO.save(testEmployee);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        int expected = 1;
        Employee testEmployee = new Employee("test", "test", null,
                new PostEmployee(3, "developer"),
                new Account(3,"kakadu","kaka321"),
                null,
                new StatusEmployee(1, "active"));

        var actual = employeeDAO.delete(testEmployee);

        Assertions.assertEquals(expected, actual);
    }
}









