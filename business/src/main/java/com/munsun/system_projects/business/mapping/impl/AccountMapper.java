package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Account;
import com.munsun.system_projects.dto.entity.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountDTO> {
    @Override
    public Account map(AccountDTO obj) {
        Account account = new Account();
        account.setId(obj.getId());
        account.setLogin(obj.getLogin());
        account.setPassword(obj.getPassword());
        return account;
    }

    @Override
    public AccountDTO reverseMap(Account obj) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(obj.getId());
        accountDTO.setLogin(obj.getLogin());
        accountDTO.setPassword(obj.getPassword());
        return accountDTO;
    }
}
