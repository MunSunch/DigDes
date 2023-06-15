package com.munsun.system_projects.dto.entity.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Команда")
public class CommandDtoIn {
    @Schema(description = "Идентификатор проекта")
    @NotNull
    @JsonProperty("project_id")
    private int idProject;
}