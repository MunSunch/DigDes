package com.munsun.system_projects.web.rest;

import com.munsun.system_projects.business.service.EmployeeService;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.AccountDtoIn;
import com.munsun.system_projects.dto.entity.in.EmployeeDtoIn;
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
@RequestMapping("/employees")
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
    public EmployeeDtoOut save(@RequestBody EmployeeDtoIn employeeDtoIn) throws Exception {
        try {
            log.debug("Обработка запроса на сохранение сотрудника: "+employeeDtoIn);
                EmployeeDtoOut result = service.createEmployee(employeeDtoIn);
            log.debug("Обработка запроса на сохранение сотрудника успешно: "+result);
                return result;
        } catch (EmployeeDuplicateException employeeDuplicateException) {
            log.error("Обработка запроса на сохранение сотрудника провалено: сотрудник уже существует"+employeeDtoIn);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, employeeDuplicateException.getMessage(), employeeDuplicateException);
        } catch (AccountDuplicateException accountDuplicateException) {
            log.error("Обработка запроса на сохранение сотрудника провалено: учетная запись уже существует;"+employeeDtoIn.getAccount());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, accountDuplicateException.getMessage(), accountDuplicateException);
        } catch (EmployeeEmptyFieldsException employeeEmptyFieldsException) {
            log.error("Обработка запроса на сохранение сотрудника провалено: обязательные поля незаполнены;"+employeeDtoIn);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, employeeEmptyFieldsException.getMessage(), employeeEmptyFieldsException);
        }
    }

    @Operation(summary = "Получение сотрудника по идентификатору")
    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:employee')")
    public EmployeeDtoOut getById(@Parameter(description = "Идентификатор") @PathVariable int id) throws EmployeeNotFoundException {
        try {
            log.debug("Обработка запроса на получение сотрудника по идентификатору: id="+id);
                var employee = service.getEmployeeById(id);
            log.debug("Обработка запроса на получение сотрудника по идентификатору успешно: employee="+employee);
                return employee;
        } catch (EmployeeNotFoundException e) {
            log.error("Обработка запроса на получение сотрудника по идентификатору провалено: id="+id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(summary = "Удаление сотрудника по идентификатору")
    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('delete:employee')")
    public EmployeeDtoOut deleteById(@Parameter(description = "Идентификатор") @RequestParam int id){
        try {
            log.debug("Обработка запроса на удаление сотрудника по идентификатору: id="+id);
                var result = service.removeEmployeeById(id);
            log.debug("Обработка запроса на удаление сотрудника по идентификатору успешно: employee="+result);
                return result;
        } catch (EmployeeNotFoundException e) {
            log.error("Обработка запроса на удаление сотрудника по идентификатору провалено: id="+id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(summary = "Изменение сотрудника по идентификатору")
    @PutMapping(value = "/updateEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:employee')")
    public EmployeeDtoOut updateById(@Parameter(description = "Идентификатор") @PathVariable int id,
                                     @RequestBody EmployeeDtoIn employeeDtoIn)
    {
        try {
            log.debug("Обработка запроса на редактирование сотрудника по идентификатору: id="+id);
                var result = service.setEmployee(id, employeeDtoIn);
            log.debug("Обработка запроса на редактирование сотрудника по идентификатору: employee="+result);
                return result;
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            log.error("Обработка запроса на редактирование сотрудника по идентификатору провалено: id="+id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage());
        } catch (Exception employeeEmptyFieldsException) {
            log.error("Обработка запроса на редактирование сотрудника по идентификатору провалено: id="+id);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, employeeEmptyFieldsException.getMessage());
        }
    }

    @Operation(summary = "Получение сотрудника по строке, которая сравнивается либо по имени, либо по фамилии," +
            "либо по отчеству, либо по электронной почте, либо по УЗ(логину)")
    @GetMapping(value = "/getEmployeesByString", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:employee')")
    public List<EmployeeDtoOut> getByString(@Parameter(description = "Строка") @RequestParam(name="string") String str) {
        log.debug("Обработка запроса на поиск сотрудников по строке: string="+str);
            var result = service.findEmployeesByString(str);
        log.debug("Обработка запроса на поиск сотрудников по строке провалено: "+result);
            return result;
    }

    @Operation(summary = "Получение сотрудника по его учетной записи")
    @GetMapping(value = "/getEmployeeByAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:employee')")
    public EmployeeDtoOut getByAccount(@Parameter(description = "Учетная запись") @RequestBody AccountDtoIn accountDtoIn) {
        try {
            log.debug("Обработка запроса на получение сотрудника по его учетной записи: "+accountDtoIn);
                var result = service.getEmployeeByAccount(accountDtoIn);
            log.debug("Обработка запроса на получение сотрудника по его учетной записи успешно: "+result);
                return result;
        } catch (EmployeeNotFoundException | AccountEmptyFieldsException employeeNotFoundException) {
            log.error("Обработка запроса на получение сотрудника по его учетной записи провалено: "+accountDtoIn);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Получение всех сотрудников")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:employee')")
    public List<EmployeeDtoOut> getAllEmployees() {
        log.debug("Обработка запроса на получение всех сотрудников...");
            var result = service.getAllEmployees();
        log.debug("Обработка запроса на получение всех сотрудников успешно: count="+result.size());
            return result;
    }
}
