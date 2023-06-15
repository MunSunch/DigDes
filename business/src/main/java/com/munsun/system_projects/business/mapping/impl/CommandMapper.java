package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.dto.entity.in.CommandDtoIn;
import com.munsun.system_projects.dto.entity.out.CommandDtoOut;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper implements Mapper<Command, CommandDtoOut> {
    private Mapper<Project, ProjectDtoOut> projectMapper;
    private Mapper<Employee, EmployeeDtoOut> employeeMapper;

    @Autowired
    public CommandMapper(Mapper<Project, ProjectDtoOut> projectMapper, Mapper<Employee, EmployeeDtoOut> employeeMapper) {
        this.projectMapper = projectMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Command map(CommandDtoOut obj) {
        return null;
    }

    @Override
    public CommandDtoOut reverseMap(Command obj) {
        CommandDtoOut commandDtoOut = new CommandDtoOut();
        commandDtoOut.setId(obj.getId());
        commandDtoOut.setCode(obj.getCode());
        commandDtoOut.setProject(projectMapper.reverseMap(obj.getProject()));
        return commandDtoOut;
    }
}
