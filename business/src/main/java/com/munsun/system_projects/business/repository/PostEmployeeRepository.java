package com.munsun.system_projects.business.tests.repository;

import com.munsun.system_projects.business.tests.model.PostEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEmployeeRepository extends JpaRepository<PostEmployee,Integer> {
    PostEmployee getPostEmployeeByName(String name);
    boolean existsPostEmployeeByName(String name);
}
