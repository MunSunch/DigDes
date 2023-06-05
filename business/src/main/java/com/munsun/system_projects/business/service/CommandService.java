package com.munsun.system_projects.business.service;

import com.munsun.system_projects.commons.enums.RoleCommand;
import com.munsun.system_projects.dto.entity.CommandDTO;
import com.munsun.system_projects.dto.entity.CommandEmployeesDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;

import java.util.List;

public interface CommandService {
    CommandDTO createCommand(CommandDTO commandDTO);
    CommandDTO addEmployeeCommand(int idProject, CommandEmployeesDTO commandEmployeesDTO);
    CommandDTO deleteEmployeeByProject(int idProject, int idEmployee);
    List<EmployeeDTO> getEmployeesByProject(int idProject);
}
