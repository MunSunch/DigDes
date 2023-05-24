package com.munsun.system_projects.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="code", unique = true)
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_project_id")
    private StatusProject status;

    @ManyToMany
    @JoinTable(name="projects_to_tasks",
        joinColumns = @JoinColumn(name="project_id"),
        inverseJoinColumns = @JoinColumn(name="task_id"))
    private List<Task> tasks;
}
