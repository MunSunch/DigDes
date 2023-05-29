package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.dto.entity.CommandDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import com.munsun.system_projects.dto.entity.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper implements Mapper<Command, CommandDTO> {
    private Mapper<Project, ProjectDTO> projectMapper;
    private Mapper<Employee, EmployeeDTO> employeeMapper;

    @Autowired
    public CommandMapper(Mapper<Project, ProjectDTO> projectMapper, Mapper<Employee, EmployeeDTO> employeeMapper) {
        this.projectMapper = projectMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Command map(CommandDTO obj) {
        Command command = new Command();
        command.setId(obj.getId());
        //command.setCode(obj.getCode());
        command.setProject(projectMapper.map(obj.getProject()));
        command.setEmployees(obj.getEmployees().stream()
                .map(employeeMapper::map).toList());
        return command;
    }

    @Override
    public CommandDTO reverseMap(Command obj) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setId(obj.getId());
        //commandDTO.setCode(obj.getCode());
        commandDTO.setProject(projectMapper.reverseMap(obj.getProject()));
        commandDTO.setEmployees(obj.getEmployees().stream()
                .map(employeeMapper::reverseMap).toList());
        return commandDTO;
    }
}
