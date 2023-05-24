package com.munsun.system_projects.domain.service.impl;

import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.domain.model.Task;
import com.munsun.system_projects.domain.service.ProjectService;
import com.munsun.system_projects.dto.entity.ProjectDTO;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public void createProject(ProjectDTO projectDTO) {

    }

    @Override
    public void setProject(int id, ProjectDTO projectDTO) {

    }

    @Override
    public ProjectDTO getProject(String str, StatusProject... statusProjects) {
        return null;
    }

    @Override
    public void setStatusProject(ProjectDTO projectDTO, StatusProject statusProject) {

    }

    @Override
    public List<Task> getTasks(int id) {
        return null;
    }
}
