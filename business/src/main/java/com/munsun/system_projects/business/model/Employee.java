package com.munsun.system_projects.business.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="lastname", nullable = false)
    private String lastname;

    @Column(name="patronymic", nullable = true)
    private String patronymic;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="post_id",  referencedColumnName = "id")
    private PostEmployee postEmployee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_id", referencedColumnName = "id")
    private Account account;

    @Column(name="email", nullable = true)
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_employees_id",  referencedColumnName = "id")
    private StatusEmployee statusEmployee;
}