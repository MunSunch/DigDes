package com.munsun.system_projects.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany
    @JoinTable(name="commands_to_employees",
               joinColumns = @JoinColumn(name="command_id"),
               inverseJoinColumns = @JoinColumn(name="employee_id"))
    private List<Employee> employees;
//    private List<Map.Entry<Employee, RoleCommand>> employees;
}
