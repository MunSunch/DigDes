package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.commons.enums.StatusProject;
import lombok.Data;

@Data
public class Project {
    private int id;
    //UQ
    private String code;
    private String name;
    private String description;
    //OneToMany
    private StatusProject status;
}
