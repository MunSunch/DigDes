package com.munsun.system_projects.domain.repository;

import com.munsun.system_projects.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
