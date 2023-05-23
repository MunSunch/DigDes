package com.munsun.system_projects.dto.entity;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class CommandDTO {
    private int id;
    private String code;
    //OneToOne
    private ProjectDTO project;
}
