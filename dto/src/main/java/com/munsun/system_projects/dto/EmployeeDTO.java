package com.munsun.system_projects.dto;

import com.munsun.system_projects.dto.enums.PostEmployeeDTO;
import com.munsun.system_projects.dto.enums.StatusEmployeeDTO;
import lombok.Data;

@Data
public class EmployeeDTO {
    private int id;
    private String name;
    private String lastname;
    private String pytronymic;
    //OneToMany
    private PostEmployeeDTO postEmployee;
    //OneToOne
    private AccountDTO account;
    //OneToMany
    private StatusEmployeeDTO statusEmployee;
}
