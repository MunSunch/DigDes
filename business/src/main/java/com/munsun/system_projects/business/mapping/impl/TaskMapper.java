package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.model.StatusTask;
import com.munsun.system_projects.business.model.Task;
import com.munsun.system_projects.dto.entity.out.EmployeeDtoOut;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
import com.munsun.system_projects.dto.entity.out.TaskDtoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
public class TaskMapper implements Mapper<Task, TaskDtoOut> {
    private Mapper<Employee, EmployeeDtoOut> employeeMapper;
    private Mapper<StatusTask, com.munsun.system_projects.commons.enums.StatusTask> statusTaskMapper;
    private Mapper<Project, ProjectDtoOut> mapperProject;

    @Autowired
    public TaskMapper(Mapper<Employee, EmployeeDtoOut> employeeMapper,
                      Mapper<StatusTask, com.munsun.system_projects.commons.enums.StatusTask> statusTaskMapper,
                      Mapper<Project, ProjectDtoOut> mapperProject)
    {
        this.employeeMapper = employeeMapper;
        this.statusTaskMapper = statusTaskMapper;
        this.mapperProject = mapperProject;
    }

    @Override
    public Task map(TaskDtoOut obj) {
        Task task = new Task();
        task.setId(obj.getId());
        task.setName(obj.getName());
        task.setDescription(obj.getDescription());
        task.setExecutor(employeeMapper.map(obj.getExecutor()));
        task.setCost(obj.getCost());
        task.setLastChangeDate(new Timestamp(new java.util.Date().getTime()));
        task.setEndDate(new Timestamp(obj.getEndDate().getTime()));
        task.setCreateDate(new Timestamp(new java.util.Date().getTime()));
        task.setStatusTask(statusTaskMapper.map(obj.getStatusTask()));
        return task;
    }

    @Override
    public TaskDtoOut reverseMap(Task obj) {
        TaskDtoOut taskDTO = new TaskDtoOut();
        taskDTO.setId(obj.getId());
        taskDTO.setName(obj.getName());
        taskDTO.setDescription(obj.getDescription());
        taskDTO.setExecutor(employeeMapper.reverseMap(obj.getExecutor()));
        taskDTO.setCost(obj.getCost());
        taskDTO.setLastChangeDate(obj.getLastChangeDate());
        taskDTO.setEndDate(obj.getEndDate());
        taskDTO.setCreateDate(obj.getCreateDate());
        taskDTO.setStatusTask(statusTaskMapper.reverseMap(obj.getStatusTask()));
        taskDTO.setProjectDtoOut(mapperProject.reverseMap(obj.getProject()));
        return taskDTO;
    }
}
