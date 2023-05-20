package com.munsun.system_projects.domain.dao.impl;

import com.munsun.system_projects.domain.dao.DAO;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.PostEmployee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostEmployeeDAO implements DAO<PostEmployee> {
    private String url;
    private String user;
    private String password;

    public PostEmployeeDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<PostEmployee> getAll() {
        String selectAllQuery = "SELECT * FROM post_employees";
        List<PostEmployee> postEmployees = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectAllQuery);
            while(set.next()) {
                PostEmployee postEmployee = new PostEmployee();
                postEmployee.setId(set.getInt("id"));
                postEmployee.setName(set.getString("name"));
                postEmployees.add(postEmployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postEmployees;
    }

    @Override
    public PostEmployee getById(int id) {
        String selectById = "SELECT * FROM post_employees WHERE id=" + id;
        PostEmployee postEmployee = null;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectById);
            postEmployee = new PostEmployee(set.getInt("id"),
                                            set.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postEmployee;
    }

    @Override
    public int save(PostEmployee obj) {
        String insertQuery = "INSERT INTO post_employees(name) VALUES " + "('" + obj.getName() + "')";
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
    public int update(int id, PostEmployee obj) {
        String updateQuery = "UPDATE post_employees SET name='" + obj.getName() + "' WHERE id=" + id;
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
    public PostEmployee deleteById(int id) {
        String deleteByIdQuery = "DELETE FROM post_employees WHERE id=" + id;
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
    public int delete(PostEmployee obj) {
        String deleteQuery = "DELETE FROM post_employees WHERE name='" + obj.getName() + "'";
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
