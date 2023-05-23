package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.commons.enums.StatusTask;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name="cost")
    private int cost;

    @Basic
    @Column(name="start_date")
    private java.sql.Date startDate;

    @Basic
    @Column(name="last_change_date")
    private java.sql.Date lastChangeDate;

    @Basic
    @Column(name="end_date")
    private java.sql.Date endDate;

    @Basic
    @Column(name="create_date")
    private java.sql.Date createDate;

    @Column(name="status_id")
    private StatusTask statusTask;
}
