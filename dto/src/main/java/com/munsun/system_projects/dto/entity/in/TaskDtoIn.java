package com.munsun.system_projects.dto.entity.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
@Schema(description = "Задача")
public class TaskDtoIn {
    @Schema(description = "Наименование")
    @NotNull
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание")
    @JsonProperty("description")
    private String description;

    @Schema(description = "идентификатор сотрудника")
    @JsonProperty("employee_id")
    private int idEmployeeExecutor;

    @Schema(description = "Трудозатраты в часах")
    @NotNull
    @JsonProperty("cost")
    private int cost;

    @Schema(hidden = true, description = "Дата последнего изменения задачи")
    @NotNull
    @JsonIgnore
    private java.sql.Timestamp lastChangeDate;

    @Schema(description = "Крайний срок, dd-MM-yyyy hh:mm:ss")
    @NotNull
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm", timezone = "Europe/Moscow")
    private java.sql.Timestamp endDate;

    @Schema(hidden = true, description = "Дата создания")
    @NotNull
    @JsonIgnore
    private java.sql.Timestamp createDate;

    @Schema(description = "Статус")
    @NotNull
    @JsonProperty("status")
    private StatusTask statusTask;

    @Schema(hidden = true, description = "идентификатор автора задачи")
    @JsonIgnore
    private int idEmployeeAuthor;

    @Schema(description = "Идентификатор проекта")
    @NotNull
    @JsonProperty("project_id")
    private int idProject;
}
