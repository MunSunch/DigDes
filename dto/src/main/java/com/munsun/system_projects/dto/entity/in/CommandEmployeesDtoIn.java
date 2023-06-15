package com.munsun.system_projects.dto.entity.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.RoleCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Команда-Сотрудник-Роль")
public class CommandEmployeesDtoIn {
    @Schema(description = "идентификатор команды")
    @Size(min=1)
    @NotNull
    @JsonProperty("command_id")
    private int idCommand;

    @Schema(description = "идентификатор сотрудника")
    @Size(min=1)
    @NotNull
    @JsonProperty("employee_id")
    private int idEmployee;

    @Schema(description = "роль в команде")
    @NotNull
    @JsonProperty("role")
    private RoleCommand roleCommand;
}
