package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.domain.model.enums.RoleCommand;
import lombok.Data;

@Data
public class ProjectsCommandsEmployees {
    private int id;
    private Command command;
    private Project project;
    private Employee employee;
    private RoleCommand role;
}
