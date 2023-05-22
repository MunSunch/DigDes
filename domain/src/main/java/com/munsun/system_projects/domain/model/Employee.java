package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="lastname")
    private String lastname;

    @Column(name="patronymic")
    private String pytronymic;

    @Enumerated(EnumType.STRING)
    @Column(name="post_id")
    private PostEmployee postEmployee;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name="email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name="status_employees_id")
    private StatusEmployee statusEmployee;
}
