package com.munsun.system_projects.dto;

import com.munsun.system_projects.domain.model.enums.RoleCommand;

public class CommandDTO {
    private int id;
    private int command;
    private ProjectDTO project;
    private EmployeeDTO employee;
    private RoleCommand role;
}
