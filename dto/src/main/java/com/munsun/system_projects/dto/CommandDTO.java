package com.munsun.system_projects.dto;

import com.munsun.system_projects.domain.model.Project;

public class CommandDTO {
    private int id;
    private String code;
    //OneToOne
    private Project project;
}
