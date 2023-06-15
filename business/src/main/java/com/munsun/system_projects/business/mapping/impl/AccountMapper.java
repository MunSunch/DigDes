package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Account;
import com.munsun.system_projects.dto.entity.out.AccountDtoOut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountDtoOut> {
    @Override
    public Account map(AccountDtoOut obj) {
        Account account = new Account();
        account.setId(obj.getId());
        account.setLogin(obj.getLogin());
        account.setPassword(obj.getPassword());
        return account;
    }

    @Override
    public AccountDtoOut reverseMap(Account obj) {
        AccountDtoOut accountDTO = new AccountDtoOut();
        accountDTO.setId(obj.getId());
        accountDTO.setLogin(obj.getLogin());
        accountDTO.setPassword(obj.getPassword());
        return accountDTO;
    }
}
