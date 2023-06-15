package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<Command, Integer> {
    Command findCommandByProject_Id(int id);
    boolean existsCommandByProject_Id(int id);
}
