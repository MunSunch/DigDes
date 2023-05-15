package com.munsun.system_projects.domain.model;

import com.munsun.system_projects.domain.model.enums.StatusProject;
import lombok.Data;

@Data
public class Project {
    private int id;
    private String code;
    private String name;
    private String description;
    //OneToMany
    private StatusProject status;

}
