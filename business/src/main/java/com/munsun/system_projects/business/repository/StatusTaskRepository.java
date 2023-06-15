package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusTaskRepository extends JpaRepository<StatusTask, Integer> {
    StatusTask findStatusTaskByName(String name);
}
