package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.StatusProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusProjectRepository extends JpaRepository<StatusProject, Integer> {
    StatusProject findByName(String name);
}
