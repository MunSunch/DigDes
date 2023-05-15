package com.munsun.system_projects.dto;

import com.munsun.system_projects.dto.enums.StatusProjectDTO;
import lombok.Data;

@Data
public class ProjectDTO {
    private int id;
    private String code;
    private String name;
    private String description;
    //OneToMany
    private StatusProjectDTO status;
}
