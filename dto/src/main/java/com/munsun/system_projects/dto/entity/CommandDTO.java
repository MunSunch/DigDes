package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusProject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Команда")
public class CommandDTO {
    @Schema(description = "Идентификатор")
    @Size(min = 1)
    @JsonProperty("id")
    private int id;

    @Schema(hidden = true, description = "код")
    @JsonIgnore
    private String code;

    @Schema(description = "Проект")
    @NotNull
    @JsonProperty("project")
    private ProjectDTO project;

    @Schema(description = "Список работников")
    @JsonProperty("employees")
    private List<EmployeeDTO> employees;

    @JsonAnySetter
    public void addProject(String name, String code,
                           String description, StatusProject statusProject)
    {
        project.setName(name);
        project.setCode(code);
        project.setDescription(description);
        project.setStatus(statusProject);
    }
}
