package com.munsun.system_projects.dto.entity;

import com.munsun.system_projects.commons.enums.StatusProject;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class ProjectDTO {
    private int id;
    private String code;
    private String name;
    private String description;
    private StatusProject status;
}
