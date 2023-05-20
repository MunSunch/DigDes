package com.munsun.system_projects.domain;

import com.munsun.system_projects.domain.dao.impl.AccountDAO;
import com.munsun.system_projects.domain.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoTest {
    private static AccountDAO accountDAO;

    @BeforeAll
    public static void setAccountDAO() {
        accountDAO = new AccountDAO("jdbc:postgresql://localhost:5432/system_projects_db",
                "postgres", "4669");
    }

    @Test
    public void testSqlInjection() {
        int expected = 1;
        Account account = new Account("',''); UPDATE accounts SET login='admin', password='admin'; -- ", "221432532dsad");

        var actual = accountDAO.save(account);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
        List<Account> expected = new ArrayList<>();
        expected.add(new Account(1, "munsun", "12345qwerty"));
        expected.add(new Account(2, "andron2007", "588425"));
        expected.add(new Account(3, "kakadu", "kaka321"));
        expected.add(new Account(4, "masha@yandex.ru", "masmmmas"));

        var actual = accountDAO.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        Account expected = new Account(1, "munsun", "12345qwerty");

        var actual = accountDAO.getById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSave() {
        int expected = 1;
        Account testAccount = new Account("testLogin", "testPassword");

        var actual = accountDAO.save(new Account( "testLogin", "testPassword"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        int expected = 1;
        Account testAccount = new Account("testLogin", "testPassword");

        var actual = accountDAO.delete(testAccount);

        Assertions.assertEquals(expected, actual);
    }
}
