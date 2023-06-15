package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.business.service.TaskService;
import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.AccountDtoIn;
import com.munsun.system_projects.dto.entity.in.TaskDtoIn;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import com.munsun.system_projects.dto.entity.out.TaskDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/tasks")
@Tag(name="Задачи", description = "методы для работы с задачами")
public class TaskRestController {
    private final TaskService service;
    private final EmployeeService employeeService;

    @Autowired
    public TaskRestController(TaskService service, EmployeeService employeeService) {
        this.service = service;
        this.employeeService = employeeService;
    }

    @Operation(summary = "Сохранение задачи")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:task')")
    public TaskDtoOut save(@RequestBody TaskDtoIn taskDtoIn) throws EmployeeNotFoundException, AccountEmptyFieldsException {
        try {
            log.debug("Обработка запроса на сохранение задачи: "+taskDtoIn);
                taskDtoIn.setIdEmployeeAuthor(getAuthorizeUser().getId());
                var result = service.createTask(taskDtoIn);
            log.debug("Обработка запроса на сохранение задачи: "+result);
                return result;
        } catch (CommandNotFoundException | CommandEmployeeNotFoundException | ProjectNotFoundException exception) {
            log.error("Обработка запроса на сохранение задачи провалено: "+taskDtoIn);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    private EmployeeDtoOut getAuthorizeUser() throws AccountEmptyFieldsException, EmployeeNotFoundException {
        log.info("Получение информации об авторизованном пользователе...");
            var user = (User) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            AccountDtoIn account = new AccountDtoIn();
            account.setLogin(user.getUsername());
            var employee = employeeService.getEmployeeByAccount(account);
        log.info("Получение информации об авторизованном пользователе: "+employee);
            return employee;
    }

    @Operation(summary = "Обновление задачи по ее идентификатору")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:task')")
    public TaskDtoOut updateTask(@Parameter(description = "идентификатор проекта") @PathVariable("id") int id, @RequestBody TaskDtoIn taskDtoIn) throws EmployeeNotFoundException, AccountEmptyFieldsException {
        try {
            log.debug("Обработка запроса на редактирование задачи по идентификатору id="+id);
                taskDtoIn.setIdEmployeeAuthor(getAuthorizeUser().getId());
                var result = service.setTask(id, taskDtoIn);
            log.debug("Обработка запроса на редактирование задачи по идентификатору id="+id+
                    " успешно");
                return result;
        } catch(ProjectNotFoundException | CommandEmployeeNotFoundException | TaskNotFoundException
            | CommandNotFoundException exception)
        {
            log.error("Обработка запроса на редактирование задачи по идентификатору id="+id+
                    " провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @Operation(summary = "Получение всех задачи по полям Наименование задачи и с применением фильтров по статусам задачи, по исполнителю, по автору задачи, по периоду крайнего срока задачи, по периоду создания задачи")
    @GetMapping(value = "/getTasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:task')")
    public List<TaskDtoOut> getTasks(@RequestBody TaskDtoIn taskDtoIn,
                                     @Parameter(description = "Статусы") @RequestParam("statuses") StatusTask ...statuses)
    {
        log.debug("Обработка запроса на поиск задачи с фильтрами: "+taskDtoIn +
                ", statuses="+ Arrays.toString(statuses));
            var result = service.getTasks(taskDtoIn, statuses);
        log.debug("Обработка запроса на поиск задачи с фильтрами: count="+result.size());
            return result;
    }

    @Operation(summary = "Обновление статуса задачи по его идентификатору")
    @PutMapping(value = "/updateStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:task')")
    public TaskDtoOut updateStatus(
            @Parameter(description = "Идентификатор") @RequestParam("id") int id,
            @Parameter(description = "Статус") @RequestParam("status") StatusTask status)
    {
        try {
            log.debug("Обработка запроса на редактирование задачи по идентификатору id=" + id);
            var result = service.updateStatusTask(id, status);
            log.debug("Обработка запроса на редактирование задачи по идентификатору успешно: " + result);
            return result;
        } catch (TaskNotFoundException taskNotFoundException) {
            log.error("Обработка запроса на редактирование задачи по идентификатору id=" + id + " провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, taskNotFoundException.getMessage());
        } catch (StatusTaskUpdateException statusTaskUpdateException) {
            log.error("Обработка запроса на редактирование задачи по идентификатору status=" + status + " провалено");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, statusTaskUpdateException.getMessage());
        }
    }
}
