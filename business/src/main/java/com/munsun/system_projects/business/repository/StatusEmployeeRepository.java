package com.munsun.system_projects.business.tests.repository;

import com.munsun.system_projects.business.tests.model.StatusEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusEmployeeRepository extends JpaRepository<StatusEmployee, Integer> {
    StatusEmployee getStatusEmployeeByName(String name);
    boolean existsStatusEmployeeByName(String name);
}
