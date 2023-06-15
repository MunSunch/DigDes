package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.RoleCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleCommandRepository extends JpaRepository<RoleCommand, Integer> {
    RoleCommand findRoleCommandByName(String name);
}
