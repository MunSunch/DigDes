package com.munsun.system_projects.domain.repository;

import com.munsun.system_projects.domain.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Integer> {
}
