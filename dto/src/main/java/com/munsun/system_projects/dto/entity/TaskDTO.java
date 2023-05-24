package com.munsun.system_projects.dto.entity;

import com.munsun.system_projects.commons.enums.StatusTask;
import lombok.Data;

import java.util.Date;

@Data
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
