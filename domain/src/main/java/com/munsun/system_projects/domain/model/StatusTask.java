package com.munsun.system_projects.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="status_tasks")
public class StatusTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;
}
