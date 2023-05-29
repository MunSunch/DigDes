package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.model.Task;
import com.munsun.system_projects.business.repository.ProjectRepository;
import com.munsun.system_projects.business.service.impl.specification.ProjectSpecification;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.business.service.ProjectService;
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
    private Mapper<com.munsun.system_projects.business.model.StatusProject, StatusProject> statusProjectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository,
                              Mapper<Project, ProjectDTO> projectMapper,
                              Mapper<Task, TaskDTO> taskMapper,
                              Mapper<com.munsun.system_projects.business.model.StatusProject, StatusProject> statusProjectMapper)
    {
        this.repository = repository;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.statusProjectMapper = statusProjectMapper;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.map(projectDTO);
        Project result = repository.save(project);
        return projectMapper.reverseMap(result);
    }

    @Override
    public ProjectDTO setProject(int id, ProjectDTO projectDTO) {
        Project project = projectMapper.map(projectDTO);
        repository.deleteById(id);
        Project result = repository.save(project);
        return projectMapper.reverseMap(result);
    }

    @Override
    public List<ProjectDTO> getProject(String str, StatusProject... statusProjects) {
        List<Project> projects = repository.findAll(ProjectSpecification.filter(str, statusProjects));
        return projects.stream()
                .map(projectMapper::reverseMap)
                .toList();
    }

    @Override
    public ProjectDTO setStatusProject(int id, StatusProject newStatus) {
        Project project = repository.getReferenceById(id);
        StatusProject oldStatus = statusProjectMapper.reverseMap(project.getStatus());
        if(oldStatus.ordinal() < newStatus.ordinal()) {
//            repository.setStatus(id, newStatus.name());
        }
        return null;
    }

    @Override
    public List<TaskDTO> getTasks(int id) {
        Project project = repository.getReferenceById(id);
        return project.getTasks().stream()
                .map(taskMapper::reverseMap)
                .toList();
    }
}
