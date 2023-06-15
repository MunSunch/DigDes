package com.munsun.system_projects.dto.entity.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.StatusProject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Проект")
public class ProjectDtoIn {
    @Schema(description = "Название")
    @NotNull
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание")
    @JsonProperty("description")
    private String description;
}
