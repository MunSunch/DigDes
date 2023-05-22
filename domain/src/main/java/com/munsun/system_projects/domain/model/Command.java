package com.munsun.system_projects.domain.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
