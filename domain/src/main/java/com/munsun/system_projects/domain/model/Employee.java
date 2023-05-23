package com.munsun.system_projects.domain.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private String pytronymic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
    private PostEmployee postEmployee;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="email", nullable = true)
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_employees_id")
    private StatusEmployee statusEmployee;
}