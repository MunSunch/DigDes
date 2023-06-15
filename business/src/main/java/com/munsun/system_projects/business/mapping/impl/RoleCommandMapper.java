package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.RoleCommand;
import org.springframework.stereotype.Component;

@Component
public class RoleCommandMapper implements Mapper<RoleCommand, com.munsun.system_projects.commons.enums.RoleCommand> {
    @Override
    public RoleCommand map(com.munsun.system_projects.commons.enums.RoleCommand obj) {
        var role = new RoleCommand();
        role.setName(obj.name());
        return role;
    }

    @Override
    public com.munsun.system_projects.commons.enums.RoleCommand reverseMap(RoleCommand obj) {
        return com.munsun.system_projects.commons.enums.RoleCommand.valueOf(obj.getName());
    }
}
