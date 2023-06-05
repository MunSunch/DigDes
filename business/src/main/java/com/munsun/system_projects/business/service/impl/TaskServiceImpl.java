package com.munsun.system_projects.business.tests.service.impl;

import com.munsun.system_projects.business.tests.repository.TaskRepository;
import com.munsun.system_projects.business.tests.service.TaskService;
import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.dto.entity.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        return null;
    }

    @Override
    public TaskDTO setTask(int id, TaskDTO taskDTO) {
        return null;
    }

    @Override
    public List<TaskDTO> getTasks(String str) {
        return null;
    }

    @Override
    public TaskDTO setStatusTask(int id, StatusTask status) {
        return null;
    }
}
