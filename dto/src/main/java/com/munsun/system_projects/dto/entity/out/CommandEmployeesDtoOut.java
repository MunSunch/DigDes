package com.munsun.system_projects.dto.entity.out;

import com.munsun.system_projects.commons.enums.RoleCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Команда-Сотрудник-Роль")
public class CommandEmployeesDtoOut {
    @Schema(description = "идентификатор")
    @Size(min=1)
    private int id;

    @Schema(description = "Команда")
    @NotNull
    private CommandDtoOut commandDTO;

    @Schema(description = "Сотрудник")
    @NotNull
    private EmployeeDtoOut employeeDTO;

    @Schema(description = "Роль в команде")
    @NotNull
    private RoleCommand roleCommand;
}
