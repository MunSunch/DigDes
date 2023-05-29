package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusProject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Проект")
public class ProjectDTO {
    @Schema(description = "Идентификатор")
    @Size(min = 1)
    @JsonProperty("id")
    private int id;

    @Schema(hidden = true, description = "код")
    @JsonIgnore
    private String code;

    @Schema(description = "Наименование")
    @NotNull
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Статус")
    @NotNull
    @JsonProperty("status")
    private StatusProject status;
}
