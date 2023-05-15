package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.domain.model.enums.RoleCommand;
import lombok.Data;

@Data
public class Command {
    private int id;
    private int command;
    private Project project;
    private Employee employee;
    private RoleCommand role;
}
