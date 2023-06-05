package com.munsun.system_projects.business.tests.service;

import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.dto.entity.ProjectDTO;
import com.munsun.system_projects.dto.entity.TaskDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDTO setProject(int id, ProjectDTO projectDTO);
    List<ProjectDTO> getProject(String str, StatusProject ...statusProjects);
    ProjectDTO setStatusProject(int id, StatusProject status);
    List<TaskDTO> getTasks(int id);
}
