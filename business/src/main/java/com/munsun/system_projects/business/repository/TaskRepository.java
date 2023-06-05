package com.munsun.system_projects.business.tests.repository;

import com.munsun.system_projects.business.tests.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
