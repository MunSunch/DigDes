package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.CommandDtoIn;
import com.munsun.system_projects.dto.entity.in.CommandEmployeesDtoIn;
import com.munsun.system_projects.dto.entity.out.CommandDtoOut;
import com.munsun.system_projects.dto.entity.out.CommandEmployeesDtoOut;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
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
    @PreAuthorize("hasAuthority('create:command')")
    public CommandDtoOut save(@RequestBody CommandDtoIn commandDtoIn) {
        try {
            log.debug("Обработка запроса на сохранение команды: command=" + commandDtoIn);
                var result = service.createCommand(commandDtoIn);
            log.debug("Обработка запроса на сохранение команды успешно: command=" + result);
                return result;
        } catch (CommandDuplicateException commandDuplicateException) {
            log.error("Обработка запроса на сохранение команды провалено: command=" + commandDtoIn);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, commandDuplicateException.getMessage());
        } catch (ProjectNotFoundException projectNotFoundException) {
            log.error("Обработка запроса на сохранение команды провалено: command=" + commandDtoIn);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, projectNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Добавить сотрудника с определенной ролью в команду по её идентификатору")
    @PutMapping(value = "/addEmployeeByProject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:command')")
    public CommandEmployeesDtoOut addEmployee(@RequestBody CommandEmployeesDtoIn commandEmployeesDtoIn) {
        try {
            log.debug("Обработка запроса на добавление сотрудника id="+commandEmployeesDtoIn.getIdEmployee() +
                    " в команду id="+commandEmployeesDtoIn.getIdCommand()+" на роль="+commandEmployeesDtoIn.getRoleCommand().name());
                var result = service.addEmployeeCommand(commandEmployeesDtoIn);
            log.debug("Обработка запроса на добавление сотрудника "+result.getEmployeeDTO() +
                    " в команду id="+result.getCommandDTO()+" на роль="+result.getRoleCommand().name()+
                    " успешно");
                return result;
        } catch (CommandNotFoundException | EmployeeNotFoundException commandNotFoundException) {
            log.error("Обработка запроса на добавление сотрудника id="+commandEmployeesDtoIn.getIdEmployee() +
                    " в команду id="+commandEmployeesDtoIn.getIdCommand()+" на роль="+commandEmployeesDtoIn.getRoleCommand().name()+
                    " провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, commandNotFoundException.getMessage());
        } catch (CommandEmployeeDuplicateException commandEmployeeDuplicateException) {
            log.error("Обработка запроса на добавление сотрудника id="+commandEmployeesDtoIn.getIdEmployee() +
                    " в команду id="+commandEmployeesDtoIn.getIdCommand()+" на роль="+commandEmployeesDtoIn.getRoleCommand().name()+
                    " провалено");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, commandEmployeeDuplicateException.getMessage());
        }
    }

    @Operation(summary = "Удаление сотрудника по идентификаторам проекта и сотрудника")
    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('delete:command')")
    public EmployeeDtoOut deleteEmployeeByProject(
            @Parameter(description = "идентификатор проекта") @RequestParam("project_id") int idProject,
            @Parameter(description = "идентификатор сотрудника") @RequestParam("employee_id") int idEmployee)
    {
        try {
            log.debug("Обработка запроса на удаление сотрудника id="+idEmployee+
                    " из проекта id="+idProject);
                var result = service.removeEmployeeByProject(idProject, idEmployee);
            log.debug("Обработка запроса на удаление сотрудника "+result+
                    " из проекта id="+idProject+" успешно");
                return result;
        } catch (EmployeeNotFoundException | ProjectNotFoundException projectNotFoundException) {
            log.error("Обработка запроса на удаление сотрудника id="+idEmployee+
                    " из проекта id="+idProject+" провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, projectNotFoundException.getMessage());
        } catch (CommandEmployeeNotFoundException | CommandNotFoundException e1) {
            log.error("Обработка запроса на удаление сотрудника id="+idEmployee+
                    " из проекта id="+idProject+" провалено");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e1.getMessage());
        }
    }

    @Operation(summary = "Получение всех сотрудников команды по идентификатору проекта")
    @GetMapping(value = "/getEmployeesByProject/{idProject}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:command')")
    public List<CommandEmployeesDtoOut> getEmployeesByProject(@Parameter(description = "Идентификатор проекта") @PathVariable int idProject) {
        try {
            log.debug("Обработка запроса на получение всех сотрудников по идентификатору проекта id="+idProject);
                var result = service.getEmployeesByProject(idProject);
            log.debug("Обработка запроса на получение всех сотрудников по идентификатору проекта id="+idProject
                +" успешно");
                return result;
        } catch (ProjectNotFoundException | CommandNotFoundException projectNotFoundException) {
            log.error("Обработка запроса на получение всех сотрудников по идентификатору проекта id="+idProject
                    +" провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, projectNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Получение всех команд")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:command')")
    public List<CommandDtoOut> getAllCommands() {
        log.debug("Обработка запроса на получение всех команд...");
            var result = service.getAllCommands();
        log.debug("Обработка запроса на получение всех команд успешно: count="+result.size());
            return result;
    }
}