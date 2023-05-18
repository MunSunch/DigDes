package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.domain.model.enums.RoleCommand;

public class CommandsEmployees {
    private int id;
    //OneToMany
    private Command command;
    //OneToMany
    private Employee employee;
    //OneToMany
    private RoleCommand roleCommand;
}
