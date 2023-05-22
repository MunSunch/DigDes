package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.commons.enums.StatusTask;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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

    @Column(name="start_date")
    private Date startDate;

    @Column(name="last_change_date")
    private Date lastChangeDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="status_id")
    private StatusTask statusTask;
}
