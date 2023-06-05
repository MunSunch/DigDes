package com.munsun.system_projects.business.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "post_employees")
public class PostEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;
}