package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.CommandEmployees;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.RoleCommand;
import com.munsun.system_projects.dto.entity.out.CommandDtoOut;
import com.munsun.system_projects.dto.entity.out.CommandEmployeesDtoOut;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandEmployeesMapper implements Mapper<CommandEmployees, CommandEmployeesDtoOut> {
    private Mapper<Command, CommandDtoOut> commandCommandDTOMapper;
    private Mapper<Employee, EmployeeDtoOut> employeeEmployeeDTOMapper;
    private Mapper<RoleCommand, com.munsun.system_projects.commons.enums.RoleCommand> roleCommandRoleCommandMapper;

    @Autowired
    public CommandEmployeesMapper(Mapper<Command, CommandDtoOut> commandCommandDTOMapper,
                                  Mapper<Employee, EmployeeDtoOut> employeeEmployeeDTOMapper,
                                  Mapper<RoleCommand, com.munsun.system_projects.commons.enums.RoleCommand> roleCommandRoleCommandMapper) {
        this.commandCommandDTOMapper = commandCommandDTOMapper;
        this.employeeEmployeeDTOMapper = employeeEmployeeDTOMapper;
        this.roleCommandRoleCommandMapper = roleCommandRoleCommandMapper;
    }

    @Override
    public CommandEmployees map(CommandEmployeesDtoOut obj) {
        CommandEmployees commandEmployees = new CommandEmployees();
        commandEmployees.setId(obj.getId());
        commandEmployees.setCommand(commandCommandDTOMapper.map(obj.getCommandDTO()));
        commandEmployees.setEmployee(employeeEmployeeDTOMapper.map(obj.getEmployeeDTO()));
        commandEmployees.setRoleCommand(roleCommandRoleCommandMapper.map(obj.getRoleCommand()));
        return commandEmployees;
    }

    @Override
    public CommandEmployeesDtoOut reverseMap(CommandEmployees obj) {
        CommandEmployeesDtoOut commandEmployeesDTO = new CommandEmployeesDtoOut();
        commandEmployeesDTO.setId(obj.getId());
        commandEmployeesDTO.setCommandDTO(commandCommandDTOMapper.reverseMap(obj.getCommand()));
        commandEmployeesDTO.setEmployeeDTO(employeeEmployeeDTOMapper.reverseMap(obj.getEmployee()));
        commandEmployeesDTO.setRoleCommand(roleCommandRoleCommandMapper.reverseMap(obj.getRoleCommand()));
        return commandEmployeesDTO;
    }
}
