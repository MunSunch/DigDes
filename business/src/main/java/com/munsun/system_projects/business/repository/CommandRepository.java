package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Integer> {
}
