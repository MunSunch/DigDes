package com.munsun.system_projects.dto.entity.out;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Задача")
public class TaskDtoOut {
    @Schema(description = "Идентификатор")
    @Size(min = 1)
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
    @JsonProperty("executor_employee")
    private EmployeeDtoOut executor;

    @Schema(description = "Трудозатраты")
    @NotNull
    @JsonProperty("cost")
    private int cost;

    @Schema(description = "Дата последнего изменения задачи")
    @JsonProperty("last_changed_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Moscow")
    private java.sql.Timestamp lastChangeDate;

    @Schema(description = "Крайний срок")
    @NotNull
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Moscow")
    private java.sql.Timestamp endDate;

    @Schema(description = "Дата создания")
    @JsonProperty("create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Moscow")
    private java.sql.Timestamp createDate;

    @Schema(description = "Статус")
    @JsonProperty("status")
    private StatusTask statusTask;

    @Schema(description = "Автор задачи")
    @JsonProperty("author_employee")
    private EmployeeDtoOut author;

    @Schema(description = "Проект")
    @NotNull
    @JsonProperty("project")
    private ProjectDtoOut projectDtoOut;

    @JsonAnySetter
    public void setEmployeeDto(EmployeeDtoOut employeeDTO) {
        executor.setId(executor.getId());
        executor.setName(employeeDTO.getName());
        executor.setLastname(executor.getLastname());
        executor.setPytronymic(executor.getPytronymic());
        executor.setPostEmployee(employeeDTO.getPostEmployee());
        executor.setAccount(employeeDTO.getAccount());
        executor.setStatusEmployee(employeeDTO.getStatusEmployee());
    }
}
