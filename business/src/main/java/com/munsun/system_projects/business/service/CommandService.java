package com.munsun.system_projects.business.service;

import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.CommandDtoIn;
import com.munsun.system_projects.dto.entity.in.CommandEmployeesDtoIn;
import com.munsun.system_projects.dto.entity.out.CommandDtoOut;
import com.munsun.system_projects.dto.entity.out.CommandEmployeesDtoOut;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;

import java.util.List;

public interface CommandService {
    CommandDtoOut createCommand(CommandDtoIn commandDtoIn) throws CommandDuplicateException, ProjectNotFoundException;
    CommandEmployeesDtoOut addEmployeeCommand(CommandEmployeesDtoIn commandEmployeesDtoIn) throws CommandNotFoundException, CommandEmployeeDuplicateException, EmployeeNotFoundException;
    EmployeeDtoOut removeEmployeeByProject(int idProject, int idEmployee) throws ProjectNotFoundException, EmployeeNotFoundException, CommandNotFoundException, CommandEmployeeNotFoundException;
    List<CommandEmployeesDtoOut> getEmployeesByProject(int idProject) throws ProjectNotFoundException, CommandNotFoundException;
    List<CommandDtoOut> getAllCommands();
    CommandDtoOut getCommandById(int id) throws CommandNotFoundException;
}
