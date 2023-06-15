package com.munsun.system_projects.business.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee executor;

    @Column(name="cost", nullable = false)
    private int cost;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_change_date")
    private java.sql.Timestamp lastChangeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date")
    private java.sql.Timestamp endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date")
    private java.sql.Timestamp createDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private StatusTask statusTask;

    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private Employee authorTask;
}