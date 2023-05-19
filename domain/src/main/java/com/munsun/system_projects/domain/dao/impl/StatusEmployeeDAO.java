package com.munsun.system_projects.domain.dao.impl;

import com.munsun.system_projects.domain.dao.DAO;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusEmployeeDAO implements DAO<StatusEmployee> {
    private String url;
    private String user;
    private String password;

    public StatusEmployeeDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<StatusEmployee> getAll() {
        String selectAllQuery = "SELECT * FROM status_employees";
        List<StatusEmployee> statusEmployees = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectAllQuery);
            while(set.next()) {
                StatusEmployee statusEmployee = new StatusEmployee();
                statusEmployee.setId(set.getInt("id"));
                statusEmployee.setName(set.getString("name"));
                statusEmployees.add(statusEmployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusEmployees;
    }

    @Override
    public StatusEmployee getById(int id) {
        String selectById = "SELECT * FROM status_employees WHERE id=" + id;
        StatusEmployee statusEmployee = null;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectById);
            statusEmployee = new StatusEmployee(set.getInt("id"),
                                                set.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusEmployee;
    }

    @Override
    public int save(StatusEmployee obj) {
        String insertQuery = "INSERT INTO status_employees(name) VALUES " + "('" + obj.getName() + "')";
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
    public int update(int id, StatusEmployee obj) {
        String updateQuery = "UPDATE status_employees SET name='" + obj.getName() + "' WHERE id=" + id;
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
    public StatusEmployee deleteById(int id) {
        String deleteByIdQuery = "DELETE FROM status_employees WHERE id=" + id;
        var removedPostEmployee = getById(id);
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            statement.executeUpdate(deleteByIdQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removedPostEmployee;
    }

    @Override
    public int delete(StatusEmployee obj) {
        String deleteQuery = "DELETE FROM status_employees WHERE name='" + obj.getName() + "'";
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
