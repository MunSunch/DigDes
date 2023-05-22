package com.munsun.system_projects.dto;

import com.munsun.system_projects.commons.enums.StatusTask;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Scope("prototype")
public class TaskDTO {
    private int id;
    private String name;
    private String description;
    private EmployeeDTO employee;
    private int cost;
    private Date startDate;
    private Date lastChangeDate;
    private Date endDate;
    private Date createDate;
    private StatusTask statusTask;
}
