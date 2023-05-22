package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.commons.enums.RoleCommand;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="commands_to_employees")
public class CommandsEmployees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private Command command;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name="role_id")
    private RoleCommand roleCommand;
}
