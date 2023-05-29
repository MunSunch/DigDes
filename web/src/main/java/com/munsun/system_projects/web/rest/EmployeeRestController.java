package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.dto.entity.AccountDTO;
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
@RequestMapping("/system_project/employees")
@Tag(name="Сотрудники", description = "Методы для работы с сотрудниками")
public class EmployeeRestController {
    private final EmployeeService service;

    @Autowired
    public EmployeeRestController(EmployeeService service) {
        this.service = service;
    }

    @Operation(summary = "Сохранение сотрудника")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:employee')")
    public EmployeeDTO save(@RequestBody EmployeeDTO employeeDTO)
    {
        EmployeeDTO result = service.createEmployee(employeeDTO);
        return result;
    }

    @Operation(summary = "Получение сотрудника по идентификатору")
    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:employee')")
    public EmployeeDTO getById(@Parameter(description = "Идентификатор") @PathVariable int id)
    {
        return service.getEmployee(id);
    }

    @Operation(summary = "Удаление сотрудника по идентификатору")
    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('delete:employee')")
    public EmployeeDTO deleteById(@Parameter(description = "Идентификатор") @RequestParam int id)
    {
        return service.removeEmployeeById(id);
    }

    @Operation(summary = "Изменение сотрудника по идентификатору")
    @PutMapping(value = "/updateEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:employee')")
    public EmployeeDTO updateById(@Parameter(description = "Идентификатор") @PathVariable int id,
            @RequestBody EmployeeDTO employeeDTO)
    {
        return service.setEmployee(id, employeeDTO);
    }

    @Operation(summary = "Получение сотрудника по строке, которая сравнивается либо по имени, либо по фамилии," +
            "либо по отчеству, либо по электронной почте")
    @GetMapping(value = "/getEmployeesByString", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:employee')")
    public List<EmployeeDTO> getByString(
            @Parameter(description = "Строка") @RequestParam(name="string") String str)
    {
        return service.getEmployees(str);
    }

    @Operation(summary = "Получение сотрудника по его учетной записи")
    @GetMapping(value = "/getEmployeeByAccount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:employee')")
    public EmployeeDTO getByAccount(@Parameter(description = "Учетная запись") @RequestBody AccountDTO accountDTO) {
        return service.getEmployee(accountDTO);
    }
}
