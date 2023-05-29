package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.RoleCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "КомандаРаботники")
public class CommandEmployeesDTO {
    @Schema(description = "Идентификатор")
    @Size(min = 1)
    @JsonProperty("id")
    private int id;

    @Schema(description = "Команда")
    @NotNull
    @JsonProperty("command")
    private CommandDTO command;

    @Schema(description = "Работник")
    @NotNull
    @JsonProperty("employee")
    private EmployeeDTO employee;

    @Schema(description = "Роль в команде")
    @NotNull
    @JsonProperty("role")
    private RoleCommand roleCommand;
}
