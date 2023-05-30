package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.repository.CommandRepository;
import com.munsun.system_projects.business.repository.EmployeeRepository;
import com.munsun.system_projects.business.repository.ProjectRepository;
import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.commons.enums.RoleCommand;
import com.munsun.system_projects.dto.entity.CommandDTO;
import com.munsun.system_projects.dto.entity.CommandEmployeesDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import com.munsun.system_projects.dto.entity.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CommandServiceImpl implements CommandService {
    private final CommandRepository commandRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    private final Mapper<Command, CommandDTO> commandMapper;
    private final Mapper<Employee, EmployeeDTO> employeeMapper;
    private final Mapper<Project, ProjectDTO> projectMapper;

    @Autowired
    public CommandServiceImpl(CommandRepository commandRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository, Mapper<Command, CommandDTO> commandMapper, Mapper<Employee, EmployeeDTO> employeeMapper, Mapper<Project, ProjectDTO> projectMapper) {
        this.commandRepository = commandRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.commandMapper = commandMapper;
        this.employeeMapper = employeeMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public CommandDTO createCommand(CommandDTO commandDTO) {
        Command command = commandMapper.map(commandDTO);
        String code = String.valueOf(new Random().nextLong());
        command.setCode(code);
        return null;
    }

    @Override
    public CommandDTO addEmployeeCommand(int idProject, CommandEmployeesDTO commandEmployeesDTO) {
        return null;
    }

    @Override
    public CommandDTO deleteEmployeeByProject(int idProject, int idEmployee) {
        return null;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByProject(int idProject) {
        return null;
    }
}
