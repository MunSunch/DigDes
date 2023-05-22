package com.munsun.system_projects.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Table(name="projects_to_tasks")
public class ProjectsTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    @Column(name="project_id")
    private Project project;

    @OneToMany
    @Column(name="task_id")
    private Task task;
}
