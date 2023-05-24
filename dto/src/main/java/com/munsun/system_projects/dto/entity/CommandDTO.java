package com.munsun.system_projects.dto.entity;

import lombok.Data;

@Data
public class CommandDTO {
    private int id;
    private String code;
    //OneToOne
    private ProjectDTO project;
}
