package com.munsun.system_projects.domain.repository;

import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select e " +
            "from Employee as e " +
            "where e.account.login =:login and e.account.password =:password")
    Employee get(@Param("login") String login,
                 @Param("password") String password);

    @Query(value = "select e " +
            "from Employee as e " +
            "where e.statusEmployee.name=:status and " +
            "e.name=:str or e.lastname=:str or e.patronymic=:str or e.email=:str")
    List<Employee> search(@Param("str") String str, @Param("status") String status);
}
