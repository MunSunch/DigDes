package com.munsun.system_projects.business.tests.service;

import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.dto.entity.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO setTask(int id, TaskDTO taskDTO);
    List<TaskDTO> getTasks(String str);
    TaskDTO setStatusTask(int id, StatusTask status);
}
