package com.munsun.system_projects.domain.repository.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.repository.AbstractRepository;

public class PostEmployeeRepository extends AbstractRepository<PostEmployee> {
    public PostEmployeeRepository(String path, Mapper<PostEmployee> mapper) {
        super(path, mapper);
    }

    @Override
    public PostEmployee getById(int id) {
        return objects.stream()
                    .filter(x -> x.getId()==id)
                    .toList()
                .get(0);
    }
}
