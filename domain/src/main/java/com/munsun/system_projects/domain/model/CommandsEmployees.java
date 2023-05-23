package com.munsun.system_projects.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="commands_to_employees")
public class CommandsEmployees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "command_id")
    private Command command;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private RoleCommand roleCommand;
}
