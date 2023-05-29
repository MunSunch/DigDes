package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusProject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Проект")
public class ProjectDTO {
    @Schema(description = "Идентификатор")
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty("id")
    private int id;

    @Schema(description = "код")
    @JsonProperty("code")
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
