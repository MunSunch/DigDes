package com.munsun.system_projects.domain.repository.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.repository.AbstractRepository;

public class AccountRepository extends AbstractRepository<Account> {
    public AccountRepository(String path, Mapper<Account> mapper) {
        super(path, mapper);
    }

    @Override
    public Account getById(int id) {
        return objects.stream()
                    .filter(x -> x.getId()==id)
                    .toList()
                .get(0);
    }
}
