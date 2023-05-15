package com.munsun.system_projects.dto;

import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;
import lombok.Data;

@Data
public class EmployeeDTO {
    private int id;
    private String name;
    private String lastname;
    private String pytronymic;
    //OneToMany
    private PostEmployee postEmployee;
    //OneToOne
    private AccountDTO account;
    //OneToMany
    private StatusEmployee statusEmployee;
}
