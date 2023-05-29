package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.commons.enums.StatusProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
//    @Query(value = """
//                update Project as p set p.status =: status
//                where p.id=:id
//                """)
//    void setStatus(@Param("id") int id,
//                   @Param("status") String status);
}