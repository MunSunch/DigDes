package com.munsun.system_projects.business.service;

import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.TaskDtoIn;
import com.munsun.system_projects.dto.entity.out.TaskDtoOut;

import java.util.List;

public interface TaskService {
    TaskDtoOut createTask(TaskDtoIn taskDTO) throws ProjectNotFoundException, EmployeeNotFoundException, CommandEmployeeNotFoundException, CommandNotFoundException;
    TaskDtoOut setTask(int id, TaskDtoIn taskDTO) throws ProjectNotFoundException, EmployeeNotFoundException, CommandEmployeeNotFoundException, CommandNotFoundException, TaskNotFoundException;
    List<TaskDtoOut> getTasks(TaskDtoIn taskDtoIn, StatusTask ...statuses);
    TaskDtoOut updateStatusTask(int id, StatusTask status) throws StatusTaskUpdateException, TaskNotFoundException;
}
