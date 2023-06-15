package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.StatusEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusEmployeeRepository extends JpaRepository<StatusEmployee, Integer> {
    StatusEmployee getStatusEmployeeByName(String name);
}
