package com.munsun.system_projects.dto.entity;

import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class EmployeeDTO {
    private int id;
    private String name;
    private String lastname;
    private String pytronymic;
    //OneToMany
    private PostEmployee postEmployee;
    //OneToOne
    private AccountDTO account;
    private String email;
    //OneToMany
    private StatusEmployee statusEmployee;
}
