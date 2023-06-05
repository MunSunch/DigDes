package com.munsun.system_projects.business.mapping.mapping.impl;

import com.munsun.system_projects.business.mapping.mapping.Mapper;
import com.munsun.system_projects.business.model.StatusProject;
import org.springframework.stereotype.Component;

@Component
public class StatusProjectMapper implements Mapper<StatusProject, com.munsun.system_projects.commons.enums.StatusProject> {
    @Override
    public StatusProject map(com.munsun.system_projects.commons.enums.StatusProject obj) {
        StatusProject statusProject = new StatusProject();
        statusProject.setName(obj.name());
        return statusProject;
    }

    @Override
    public com.munsun.system_projects.commons.enums.StatusProject reverseMap(StatusProject obj) {
        String name = obj.getName();
        return com.munsun.system_projects.commons.enums.StatusProject.valueOf(name);
    }
}
