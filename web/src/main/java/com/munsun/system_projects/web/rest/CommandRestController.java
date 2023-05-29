package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.commons.enums.RoleCommand;
import com.munsun.system_projects.dto.entity.CommandDTO;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/commands")
@Tag(name="Команды", description = "Методы для работы с командами")
public class CommandRestController {
    private final CommandService service;

    @Autowired
    public CommandRestController(CommandService service) {
        this.service = service;
    }

    @Operation(summary = "Сохранение команды")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('command:create')")
    public CommandDTO save(@RequestBody CommandDTO commandDTO) {
        return service.createCommand(commandDTO);
    }

    @Operation(summary = "Добавить сотрудника с определенной ролью в команду по её идентификатору")
    @PutMapping(value = "/addEmployeeByProject/{idProject}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('command:update')")
    public CommandDTO addEmployee(
            @Parameter(description = "Идентификатор проекта") @PathVariable int idProject,
            @Parameter(description = "Сотрудник") @RequestBody EmployeeDTO employeeDTO,
            @Parameter(description = "Роль в команде") @RequestParam("role") RoleCommand roleCommand)
    {
        return service.addEmployeeCommand(idProject, employeeDTO, roleCommand);
    }

    @Operation(summary = "Удаление сотрудника по идентификаторам проекта и сотрудника")
    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('command:delete')")
    public CommandDTO deleteEmployeeByProject(
            @Parameter(description = "идентификатор проекта") @RequestParam("project_id") int idProject,
            @Parameter(description = "идентификатор сотрудника") @RequestParam("employee_id") int idEmployee)
    {
        return service.deleteEmployeeByProject(idProject, idEmployee);
    }

    @Operation(summary = "Получение всех сотрудников команды по идентификатору проекта")
    @GetMapping(value = "/getEmployeesByProject/{idProject}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('command:read')")
    public List<EmployeeDTO> getEmployeesByProject(@Parameter(description = "Идентификатор проекта") @PathVariable int idProject) {
        return service.getEmployeesByProject(idProject);
    }
}
