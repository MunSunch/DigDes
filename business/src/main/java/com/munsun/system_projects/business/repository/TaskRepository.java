package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
