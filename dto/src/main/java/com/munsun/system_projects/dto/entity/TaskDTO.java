package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Задача")
public class TaskDTO {
    @Schema(description = "Идентификатор")
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty("id")
    private int id;

    @Schema(description = "Наименование")
    @NotNull
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Сотрудник")
    @JsonProperty("employee")
    private EmployeeDTO employee;

    @Schema(description = "Трудозатраты")
    @NotNull
    @JsonProperty("cost")
    private int cost;

    @Schema(description = "Дата старта")
    @NotNull
    @JsonProperty("startDate")
    private Date startDate;

    @Schema(description = "Дата последнего изменения задачи")
    @NotNull
    @JsonProperty("lastChangeDate")
    private Date lastChangeDate;

    @Schema(description = "Крайний срок")
    @NotNull
    @JsonProperty("endDate")
    private Date endDate;

    @Schema(description = "Дата создания")
    @NotNull
    @JsonProperty("createDate")
    private Date createDate;

    @Schema(description = "Статус")
    @NotNull
    @JsonProperty("status")
    private StatusTask statusTask;

    @JsonAnySetter
    public void setEmployeeDto(EmployeeDTO employeeDTO) {
        employee.setId(employee.getId());
        employee.setName(employeeDTO.getName());
        employee.setLastname(employee.getLastname());
        employee.setPytronymic(employee.getPytronymic());
        employee.setPostEmployee(employeeDTO.getPostEmployee());
        employee.setAccount(employeeDTO.getAccount());
        employee.setStatusEmployee(employeeDTO.getStatusEmployee());
    }
}
