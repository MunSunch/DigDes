package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.repository.CommandRepository;
import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.dto.entity.CommandDTO;
import com.munsun.system_projects.dto.entity.CommandEmployeesDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandServiceImpl implements CommandService {
    private CommandRepository commandRepository;

    @Autowired
    public CommandServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public CommandDTO createCommand(CommandDTO commandDTO) {
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
