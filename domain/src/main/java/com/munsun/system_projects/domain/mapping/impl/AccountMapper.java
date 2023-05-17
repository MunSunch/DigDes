package com.munsun.system_projects.domain.mapping.impl;

import com.munsun.system_projects.domain.mapping.Mapper;
import com.munsun.system_projects.domain.mapping.Parser;
import com.munsun.system_projects.domain.model.Account;

public class AccountMapper extends Mapper<Account> {
    public AccountMapper(Parser parser) {
        super(parser);
    }

    @Override
    public Account mapObject(String line) {
        String[] values = parser.parse(line);
        Account account = new Account();
        account.setId(Integer.parseInt(values[0]));
        account.setLogin(values[1]);
        account.setPassword(values[2]);
        return account;
    }

    @Override
    public String mapString(Account account) {
        return account.getId() + ","
                + account.getLogin() + ","
                + account.getPassword();
    }
}
