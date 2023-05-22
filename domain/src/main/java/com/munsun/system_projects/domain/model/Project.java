package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.commons.enums.StatusProject;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="status_project_id")
    private StatusProject status;
}
