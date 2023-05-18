package com.munsun.system_projects.domain.dao.impl;

import com.munsun.system_projects.domain.dao.DAO;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;
import com.munsun.system_projects.dto.EmployeeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    private String url;
    private String user;
    private String password;

    private DAO<Account> accountDAO;
    private DAO<PostEmployee> postEmployeeDAO;
    private DAO<StatusEmployee> statusEmployeeDAO;

    public EmployeeDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        accountDAO = new AccountDAO(url,user,password);
        postEmployeeDAO = new PostEmployeeDAO(url,user,password);
        statusEmployeeDAO = new StatusEmployeeDAO(url,user,password);
    }

    @Override
    public List<Employee> getAll() {
        String selectAllQuery = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectAllQuery);
            while(set.next()) {
                Employee employee = new Employee();
                employee.setId(set.getInt("id"));
                employee.setName(set.getString("name"));
                employee.setLastname(set.getString("lastname"));
                employee.setPytronymic(set.getString("patronymic"));
                employee.setPostEmployee(postEmployeeDAO.getById(set.getInt("post_id")));
                employee.setAccount(accountDAO.getById(set.getInt("account_id")));
                employee.setEmail(set.getString("email"));
                employee.setStatusEmployee(statusEmployeeDAO.getById(set.getInt("status_employees_id")));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getById(int id) {
        return getAll().stream()
                .filter(x -> x.getId()==id)
                .toList()
                .get(0);
    }

    @Override
    public int save(Employee obj) {
        String email = obj.getEmail()==null? null: "'" + obj.getEmail() + "'";
        String patronymic = obj.getPytronymic()==null? null: "'" + obj.getPytronymic() + "'";
        String insertQuery = String.format("INSERT INTO employees(name,lastname,patronymic,post_id,account_id,email,status_employees_id)" +
                "VALUES ('%s', '%s', %s, %d, %d, %s, %d)", obj.getName(), obj.getLastname(),
                patronymic, obj.getPostEmployee().getId(), obj.getAccount().getId(),
                email, obj.getStatusEmployee().getId());
        int counter = 0;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            counter = statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public int update(int id, Employee obj) {
        String email = obj.getEmail()==null? null: "'" + obj.getEmail() + "'";
        String patronymic = obj.getPytronymic()==null? null: "'" + obj.getPytronymic() + "'";
        String updateQuery = String.format("UPDATE FROM employees SET name='%s', lastname='%s', patronymic=%s, " +
                "post_id=%d, account_id=%d, email=%s, status_employees_id=%d WHERE id=%d", obj.getName(),
                obj.getLastname(), patronymic, obj.getPostEmployee().getId(), obj.getAccount().getId(),
                email, obj.getStatusEmployee().getId());
        int counter = 0;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            counter = statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public Employee deleteById(int id) {
        String deleteByIdQuery = "DELETE FROM employees WHERE id=" + id;
        Employee removedEmployee = getById(id);
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            statement.executeUpdate(deleteByIdQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removedEmployee;
    }

    @Override
    public int delete(Employee obj) {
        String email = obj.getEmail()==null? null: "'" + obj.getEmail() + "'";
        String patronymic = obj.getPytronymic()==null? null: "'" + obj.getPytronymic() + "'";
        String deleteQuery=String.format("DELETE FROM employees WHERE name='%s' AND " +
                "lastname='%s' AND patronymic=%s AND post_id=%d AND account_id=%d AND" +
                " email=%s AND status_employees_id=%d", obj.getName(), obj.getLastname(),
                patronymic, obj.getPostEmployee().getId(), obj.getAccount().getId(),
                email, obj.getStatusEmployee().getId());
        int counter = 0;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            counter = statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}