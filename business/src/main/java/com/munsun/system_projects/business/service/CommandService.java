package com.munsun.system_projects.business.service;

import com.munsun.system_projects.commons.enums.RoleCommand;
import com.munsun.system_projects.dto.entity.CommandDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;

import java.util.List;

public interface CommandService {
    CommandDTO createCommand(CommandDTO commandDTO);
    CommandDTO addEmployeeCommand(int idProject, EmployeeDTO employeeDTO, RoleCommand roleCommand);
    CommandDTO deleteEmployeeByProject(int idProject, int idEmployee);
    List<EmployeeDTO> getEmployeesByProject(int idProject);
}
