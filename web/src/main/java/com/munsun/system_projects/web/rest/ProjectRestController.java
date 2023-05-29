package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.ProjectService;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.dto.entity.ProjectDTO;
import com.munsun.system_projects.dto.entity.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('project:create')")
    public ProjectDTO save(@RequestBody ProjectDTO projectDTO) {
        return service.createProject(projectDTO);
    }

    @Operation(summary = "Обновление проекта по его идентификатору")
    @PutMapping(value = "/updateProject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('project:update')")
    public ProjectDTO update(
            @Parameter(description = "Идентификатор") @RequestParam int id,
            @RequestBody ProjectDTO projectDTO)
    {
        return service.setProject(id, projectDTO);
    }

    @Operation(summary = "Получение проекта по строке, которая может быть его наименованием или описанием и с применением фильтров по статусу проекта")
    @GetMapping(value = "/getProject/{str}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('project:read')")
    public List<ProjectDTO> getProject(
            @Parameter(description = "Строка") @PathVariable String str,
            @Parameter(description = "Статусы проекта") @RequestParam StatusProject...statusProjects)
    {
        return service.getProject(str, statusProjects);
    }

    @Operation(summary = "Обновление статуса проекта по его идентификатору")
    @PutMapping(value = "/updateStatusProject/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('project:update')")
    public ProjectDTO updateStatus(
            @Parameter(description = "Идентификатор") @PathVariable int id,
            @Parameter(description = "Новый статус проекта") @RequestParam StatusProject status)
    {
        return service.setStatusProject(id, status);
    }

    @Operation(summary = "Получение всех задач проекта по его идентификатору")
    @GetMapping(value = "/getTasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('project:read')")
    public List<TaskDTO> getTasks(@Parameter(description = "Идентификатор") @PathVariable int id) {
        return service.getTasks(id);
    }
}
