package com.munsun.system_projects.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String name;
    private String lastname;
    private String pytronymic;
    //OneToMany
    private String postEmployee;
    //OneToOne
    private String account;
    private String email;
    //OneToMany
    private String statusEmployee;
}
