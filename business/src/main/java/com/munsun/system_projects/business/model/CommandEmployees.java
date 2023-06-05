package com.munsun.system_projects.business.tests.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="command_to_employees")
public class CommandEmployees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "command_id", nullable = false)
    private Command command;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleCommand roleCommand;
}
