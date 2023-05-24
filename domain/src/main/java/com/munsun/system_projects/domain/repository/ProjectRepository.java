package com.munsun.system_projects.domain.repository;

import com.munsun.system_projects.domain.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
