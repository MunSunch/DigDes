package com.munsun.system_projects.domain.repository.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.model.StatusEmployee;
import com.munsun.system_projects.domain.repository.AbstractRepository;


public class StatusEmployeeRepository extends AbstractRepository<StatusEmployee> {
    public StatusEmployeeRepository(String path, Mapper<StatusEmployee> mapper) {
        super(path, mapper);
    }

    @Override
    public StatusEmployee getById(int id) {
        return objects.stream()
                    .filter(x -> x.getId()==id)
                    .toList()
                .get(0);
    }
}
