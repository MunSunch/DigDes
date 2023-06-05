package com.munsun.system_projects.business.tests.service.impl;

import com.munsun.system_projects.business.tests.mapping.Mapper;
import com.munsun.system_projects.business.tests.model.Project;
import com.munsun.system_projects.business.tests.model.Task;
import com.munsun.system_projects.business.tests.repository.ProjectRepository;
import com.munsun.system_projects.business.tests.service.ProjectService;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.dto.entity.ProjectDTO;
import com.munsun.system_projects.dto.entity.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository repository;
    private Mapper<Project, ProjectDTO> projectMapper;
    private Mapper<Task, TaskDTO> taskMapper;
    private Mapper<com.munsun.system_projects.business.tests.model.StatusProject, StatusProject> statusProjectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository,
                              Mapper<Project, ProjectDTO> projectMapper,
                              Mapper<Task, TaskDTO> taskMapper,
                              Mapper<com.munsun.system_projects.business.tests.model.StatusProject, StatusProject> statusProjectMapper)
    {
        this.repository = repository;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.statusProjectMapper = statusProjectMapper;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public ProjectDTO setProject(int id, ProjectDTO projectDTO) {
        return null;

    }

    @Override
    public List<ProjectDTO> getProject(String str, StatusProject... statusProjects) {
        return null;

    }

    @Override
    public ProjectDTO setStatusProject(int id, StatusProject newStatus) {
        return null;

    }

    @Override
    public List<TaskDTO> getTasks(int id) {
        return null;
    }
}
