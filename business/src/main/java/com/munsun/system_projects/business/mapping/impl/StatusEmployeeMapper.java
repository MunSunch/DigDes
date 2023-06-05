package com.munsun.system_projects.business.mapping.mapping.impl;

import com.munsun.system_projects.business.mapping.mapping.Mapper;
import com.munsun.system_projects.business.model.StatusEmployee;
import org.springframework.stereotype.Component;

@Component
public class StatusEmployeeMapper implements Mapper<StatusEmployee, StatusEmployee> {
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
