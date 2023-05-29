package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.TaskService;
import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.dto.entity.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name="Задачи", description = "методы для работы с задачами")
public class TaskRestController {
    private final TaskService service;

    @Autowired
    public TaskRestController(TaskService service) {
        this.service = service;
    }

    @Operation(summary = "Сохранение задачи")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('task:create')")
    public TaskDTO save(@RequestBody TaskDTO taskDTO) {
        return service.createTask(taskDTO);
    }

    @Operation(summary = "Обновление задачи по ее идентификатору")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('task:update')")
    public TaskDTO update(@RequestParam("Идентификатор") int id, @RequestBody TaskDTO taskDTO) {
        return service.setTask(id, taskDTO);
    }

    @Operation(summary = "Получение всех задачи по строке, которая сравнивается ")
    @GetMapping(value = "/getTasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('task:read')")
    public List<TaskDTO> getTasks(@RequestParam("Строка") String str) {
        return service.getTasks(str);
    }

    @Operation(summary = "Обновление статуса задачи по его идентификатору")
    @PutMapping(value = "/updateStatus/{id}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('task:update')")
    public TaskDTO updateStatus(
            @RequestParam("Идентификатор") @PathVariable int id,
            @RequestParam("Статус задачи") @PathVariable StatusTask status)
    {
        return service.setStatusTask(id, status);
    }
}
