package com.munsun.system_projects.domain.service;

import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.dto.entity.ProjectDTO;

public interface ProjectService {
    void createProject(ProjectDTO projectDTO);
    void setProject(int id, ProjectDTO projectDTO);
    ProjectDTO getProject(String str, StatusProject ...statusProjects);
    void setStatusProject(ProjectDTO projectDTO, StatusProject statusProject);
}
