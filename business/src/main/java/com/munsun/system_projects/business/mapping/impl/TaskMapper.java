package com.munsun.system_projects.business.mapping.mapping.impl;

import com.munsun.system_projects.business.mapping.mapping.Mapper;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.StatusTask;
import com.munsun.system_projects.business.model.Task;
import com.munsun.system_projects.dto.entity.EmployeeDTO;
import com.munsun.system_projects.dto.entity.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TaskMapper implements Mapper<Task, TaskDTO> {
    private Mapper<Employee, EmployeeDTO> employeeMapper;
    private Mapper<StatusTask, com.munsun.system_projects.commons.enums.StatusTask> statusTaskMapper;

    @Autowired
    public TaskMapper(Mapper<Employee, EmployeeDTO> employeeMapper,
                      Mapper<StatusTask, com.munsun.system_projects.commons.enums.StatusTask> statusTaskMapper) {
        this.employeeMapper = employeeMapper;
        this.statusTaskMapper = statusTaskMapper;
    }

    @Override
    public Task map(TaskDTO obj) {
        Task task = new Task();
        task.setId(obj.getId());
        task.setName(obj.getName());
        task.setDescription(obj.getDescription());
        task.setEmployee(employeeMapper.map(obj.getEmployee()));
        task.setCost(obj.getCost());
        task.setStartDate(new Date(obj.getStartDate().getTime()));
        task.setLastChangeDate(new Date(obj.getLastChangeDate().getTime()));
        task.setEndDate(new Date(obj.getEndDate().getTime()));
        task.setCreateDate(new Date(obj.getCreateDate().getTime()));
        task.setStatusTask(statusTaskMapper.map(obj.getStatusTask()));
        return task;
    }

    @Override
    public TaskDTO reverseMap(Task obj) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(obj.getId());
        taskDTO.setName(obj.getName());
        taskDTO.setDescription(obj.getDescription());
        taskDTO.setEmployee(employeeMapper.reverseMap(obj.getEmployee()));
        taskDTO.setCost(obj.getCost());
        taskDTO.setStartDate((java.util.Date) obj.getStartDate());
        taskDTO.setLastChangeDate((java.util.Date) obj.getLastChangeDate());
        taskDTO.setEndDate((java.util.Date) obj.getEndDate());
        taskDTO.setCreateDate((java.util.Date) obj.getCreateDate());
        taskDTO.setStatusTask(statusTaskMapper.reverseMap(obj.getStatusTask()));
        return taskDTO;
    }
}
