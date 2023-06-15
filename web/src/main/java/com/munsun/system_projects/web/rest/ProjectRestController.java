package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.ProjectService;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.commons.exceptions.ProjectDuplicateException;
import com.munsun.system_projects.commons.exceptions.ProjectEmptyFieldsException;
import com.munsun.system_projects.commons.exceptions.ProjectIncorrectStatusUpdateException;
import com.munsun.system_projects.commons.exceptions.ProjectNotFoundException;
import com.munsun.system_projects.dto.entity.in.ProjectDtoIn;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
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

import java.util.Arrays;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/projects")
@Tag(name="Проекты", description = "методы для управления проектами")
public class ProjectRestController {
    private ProjectService service;

    @Autowired
    public ProjectRestController(ProjectService service) {
        this.service = service;
    }

    @Operation(summary = "Сохранение проекта")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:project')")
    public ProjectDtoOut save(@RequestBody ProjectDtoIn projectDtoIn) {
        try {
            log.debug("Обработка запроса на сохранение проекта: "+projectDtoIn);
                var result = service.createProject(projectDtoIn);
            log.debug("Обработка запроса на сохранение проекта успешно: "+result);
                return result;
        } catch (ProjectEmptyFieldsException | ProjectDuplicateException projectDuplicateException) {
            log.error("Обработка запроса на сохранение проекта провалено: "+projectDtoIn);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, projectDuplicateException.getMessage());
        }
    }

    @Operation(summary = "Обновление проекта по его идентификатору")
    @PutMapping(value = "/updateProject/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:project')")
    public ProjectDtoOut update(
            @Parameter(description = "Идентификатор") @PathVariable int id,
            @RequestBody ProjectDtoIn projectDtoIn)
    {
        try {
            log.debug("Обработка запроса на редактирование проекта по его идентификатору: id="+id);
                var result = service.setProject(id, projectDtoIn);
            log.debug("Обработка запроса на редактирование проекта по его идентификатору: "+result);
                return result;
        } catch (ProjectEmptyFieldsException | ProjectDuplicateException exception) {
            log.error("Обработка запроса на редактирование проекта по его идентификатору провалено: "+id);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (ProjectNotFoundException projectNotFoundException) {
            log.error("Обработка запроса на редактирование проекта по его идентификатору провалено: "+id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, projectNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Получение проекта по строке, которая может быть его наименованием или описанием и с применением фильтров по статусу проекта")
    @GetMapping(value = "/getProject", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:project')")
    public List<ProjectDtoOut> getProject(
            @Parameter(description = "Строка") @RequestParam(name="string") String str,
            @Parameter(description = "Статусы проекта") @RequestParam(name="status") StatusProject...statusProjects)
    {
        log.debug("Обработка запроса на поиск проекта по строке string="+str+
                " , статусам="+ Arrays.toString(statusProjects));
            var result = service.getProjectsByStringAndStatuses(str, statusProjects);
        log.debug("Обработка запроса на поиск проекта по строке string="+str+
                " , статусам="+ Arrays.toString(statusProjects) +" успешно: count="+result.size());
            return result;
    }

    @Operation(summary = "Обновление статуса проекта по его идентификатору")
    @PutMapping(value = "/updateStatusProject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:project')")
    public ProjectDtoOut updateStatus(
            @Parameter(description = "Идентификатор") @PathVariable int id,
            @Parameter(description = "Новый статус проекта") @RequestParam(name = "status") StatusProject status)
    {
        try {
            log.debug("Обработка запроса на редактирование статуса проекта id="+id+
                    ", status="+status.name());
                var result = service.updateStatusProject(id, status);
            log.debug("Обработка запроса на редактирование статуса проекта id="+id+
                    ", status="+status.name()+" успешно: "+result);
                return result;
        } catch (ProjectNotFoundException projectNotFoundException) {
            log.error("Обработка запроса на редактирование статуса проекта id="+id+" провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, projectNotFoundException.getMessage());
        } catch (ProjectIncorrectStatusUpdateException projectIncorrectStatusUpdateException) {
            log.error("Обработка запроса на редактирование статуса проекта status="+status.name()+" провалено");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, projectIncorrectStatusUpdateException.getMessage());
        } catch (ProjectEmptyFieldsException e) {
            log.error("Обработка запроса на редактирование статуса проекта id="+id+" провалено");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
