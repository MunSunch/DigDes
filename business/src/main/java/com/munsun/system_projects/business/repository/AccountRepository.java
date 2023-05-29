package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    @Override
    <S extends Account> boolean exists(Example<S> example);
}
