package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.CommandEmployees;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.RoleCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface CommandEmployeeRepository extends JpaRepository<CommandEmployees, Integer> {
    boolean existsCommandEmployeesByCommandAndEmployee(Command command, Employee employee);
    List<CommandEmployees> findCommandEmployeesByCommand(Command command);

    @Transactional
    void deleteCommandEmployeesByCommandAndEmployee(Command command, Employee employee);
}
