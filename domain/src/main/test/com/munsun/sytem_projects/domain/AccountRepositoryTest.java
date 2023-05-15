package com.munsun.sytem_projects.domain;

import com.munsun.system_projects.domain.mapping.read_write_files.impl.AccountMapper;
import com.munsun.system_projects.domain.mapping.read_write_files.impl.CSVParser;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.repository.impl.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountRepositoryTest {
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setAccountRepository() {
        accountRepository = new AccountRepository("src/main/resources/txt/accounts_test.txt",
                new AccountMapper(new CSVParser()));
    }

    @AfterEach
    public void resetAccountRepository() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, "munsun", "12345qwerty"));
        accounts.add(new Account(2, "andron1997", "588425"));

        accountRepository.clear();
        accountRepository.create(accounts.get(0));
        accountRepository.create(accounts.get(1));
        accountRepository.close();
    }

    @Test
    public void testRead() {
        Set<Account> expected = new HashSet<>();
        expected.add(new Account(1, "munsun", "12345qwerty"));
        expected.add(new Account(2, "andron1997", "588425"));

        accountRepository.readFile();
        Set<Account> actual = accountRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        Set<Account> expected = new HashSet<>();
        expected.add(new Account(1, "munsun", "12345qwerty"));
        expected.add(new Account(2, "andron1997", "588425"));
        expected.add(new Account(3,"tolya", "2005qwer"));

        accountRepository.readFile();
        accountRepository.create(new Account(3,"tolya", "2005qwer"));
        accountRepository.close();
        accountRepository.readFile();
        Set<Account> actual = accountRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        Set<Account> expected = new HashSet<>();
        expected.add(new Account(1, "munsun", "12345qwerty"));
        Account updateAccount = new Account(15, "masha", "-");
        expected.add(updateAccount);

        accountRepository.readFile();
        accountRepository.update(2, updateAccount);
        accountRepository.writeFile();
        var actual = accountRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDeleteById() {
        Set<Account> expected = new HashSet<>();
        expected.add(new Account(2, "andron1997", "588425"));

        accountRepository.readFile();
        accountRepository.deleteById(1);
        accountRepository.close();
        accountRepository.readFile();
        var actual = accountRepository.getAll();

        Assertions.assertEquals(expected, actual);
    }
}
