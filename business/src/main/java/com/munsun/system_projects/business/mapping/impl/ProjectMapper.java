package com.munsun.system_projects.business.mapping.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.model.StatusProject;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements Mapper<Project, ProjectDtoOut> {
    private Mapper<StatusProject, com.munsun.system_projects.commons.enums.StatusProject> mapperStatusProject;

    @Autowired
    public ProjectMapper(Mapper<StatusProject, com.munsun.system_projects.commons.enums.StatusProject> mapperStatusProject) {
        this.mapperStatusProject = mapperStatusProject;
    }

    @Override
    public Project map(ProjectDtoOut obj) {
        Project project = new Project();
        project.setId(obj.getId());
        project.setName(obj.getName());
        project.setCode(obj.getCode());
        project.setDescription(obj.getDescription());
        project.setStatus(mapperStatusProject.map(obj.getStatus()));
        return project;
    }

    @Override
    public ProjectDtoOut reverseMap(Project obj) {
        ProjectDtoOut projectDTO = new ProjectDtoOut();
        projectDTO.setId(obj.getId());
        projectDTO.setCode(obj.getCode());
        projectDTO.setName(obj.getName());
        projectDTO.setDescription(obj.getDescription());
        projectDTO.setStatus(mapperStatusProject.reverseMap(obj.getStatus()));
        return projectDTO;
    }
}
