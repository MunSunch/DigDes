package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Account;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    @Query(value = """
                select e 
                from Employee as e 
                where e.account.login =:#{account.login} and e.account.password =:#{account.password}
           """)
    Employee get(Account account);

    @Query(value = """
            select e  
            from Employee as e 
            where e.statusEmployee.name=:status and 
            e.name=:str or e.lastname=:str or e.patronymic=:str or e.email=:str
            """)
    List<Employee> search(@Param("str") String str, @Param("status") String status);

    @Query(value = """
            update Employee as e set e.statusEmployee=:#{#status.name()} 
            where e.id=:id
            """)
    void setStatus(@Param("id") int id, @Param("status") StatusEmployee status);

    Optional<Employee> findEmployeeByAccount_Login(String login);
}
