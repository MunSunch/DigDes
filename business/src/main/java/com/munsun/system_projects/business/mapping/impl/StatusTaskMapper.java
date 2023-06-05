package com.munsun.system_projects.business.mapping.mapping.impl;

import com.munsun.system_projects.business.mapping.mapping.Mapper;
import com.munsun.system_projects.business.model.StatusTask;
import org.springframework.stereotype.Component;

@Component
public class StatusTaskMapper implements Mapper<StatusTask, StatusTask> {
    @Override
    public StatusTask map(com.munsun.system_projects.commons.enums.StatusTask obj) {
        StatusTask statusTask = new StatusTask();
        statusTask.setName(obj.name());
        return statusTask;
    }

    @Override
    public com.munsun.system_projects.commons.enums.StatusTask reverseMap(StatusTask obj) {
        String name = obj.getName();
        return com.munsun.system_projects.commons.enums.StatusTask.valueOf(name);
    }
}
