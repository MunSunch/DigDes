package com.munsun.system_projects.domain.dao.impl;

import com.munsun.system_projects.domain.dao.DAO;
import com.munsun.system_projects.domain.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account> {
    private String url;
    private String user;
    private String password;

    public AccountDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Account> getAll() {
        String selectAllQuery = "SELECT * FROM accounts";
        List<Account> accounts = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectAllQuery);
            while(set.next()) {
                int id = set.getInt("id");
                String login = set.getString("login");
                String password = set.getString("password");
                accounts.add(new Account(id,login,password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getById(int id) {
        String selectById = "SELECT * FROM accounts WHERE id=" + id;
        Account account = null;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            ResultSet set = statement.executeQuery(selectById);
            while(set.next()) {
                account = new Account(set.getInt("id"),
                        set.getString("login"),
                        set.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public int save(Account obj) {
        String createAccountQuery = "INSERT INTO accounts(login, password) VALUES (?, ?)";
        int counter = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createAccountQuery))
        {
            preparedStatement.setString(1, obj.getLogin());
            preparedStatement.setString(2, obj.getPassword());
            counter = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public int update(int id, Account newAccount) {
        String updateQuery = "UPDATE accounts SET login=?, password=? WHERE id=?";
        int counter = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery))
        {
            preparedStatement.setString(1, newAccount.getLogin());
            preparedStatement.setString(2, newAccount.getPassword());
            preparedStatement.setInt(3, id);
            counter = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public Account deleteById(int id) {
        String deleteQuery = String.format("DELETE FROM accounts" +
                "WHERE id=%d", id);
        Account removedAccount = getById(id);
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement())
        {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removedAccount;
    }

    @Override
    public int delete(Account obj) {
        String deleteQuery = String.format("DELETE FROM accounts WHERE login='%s' AND password='%s'",
                obj.getLogin(),obj.getPassword());
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
