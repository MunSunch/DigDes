package com.munsun.system_projects.business.repository;

import com.munsun.system_projects.business.model.PostEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEmployeeRepository extends JpaRepository<PostEmployee,Integer> {
    PostEmployee getPostEmployeeByName(String name);
    boolean existsPostEmployeeByName(String name);
}
