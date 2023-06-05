package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
//    @Query(value = """
//                update Project as p set p.status =: status
//                where p.id=:id
//                """)
//    void setStatus(@Param("id") int id,
//                   @Param("status") String status);
}