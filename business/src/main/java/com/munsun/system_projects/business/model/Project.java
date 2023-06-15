package com.munsun.system_projects.business.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="code", unique = true, nullable = false)
    private String code;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_project_id")
    private StatusProject status;
}
