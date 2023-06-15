package com.munsun.system_projects.business.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="commands_to_employees")
public class CommandEmployees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "command_id", nullable = false)
    private Command command;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RoleCommand roleCommand;
}
