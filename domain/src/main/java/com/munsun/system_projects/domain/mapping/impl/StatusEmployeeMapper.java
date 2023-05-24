package com.munsun.system_projects.domain.mapping.impl;

import com.munsun.system_projects.domain.mapping.Mapper;
import com.munsun.system_projects.domain.model.StatusEmployee;
import org.springframework.stereotype.Component;

@Component
public class StatusEmployeeMapper implements Mapper<StatusEmployee, com.munsun.system_projects.commons.enums.StatusEmployee> {
    @Override
    public StatusEmployee map(com.munsun.system_projects.commons.enums.StatusEmployee obj) {
        String name = obj.name();
        StatusEmployee statusEmployee = new StatusEmployee();
        statusEmployee.setName(name);
        return statusEmployee;
    }

    @Override
    public com.munsun.system_projects.commons.enums.StatusEmployee reverseMap(StatusEmployee obj) {
        String name = obj.getName();
        return com.munsun.system_projects.commons.enums.StatusEmployee.valueOf(name);
    }
}
