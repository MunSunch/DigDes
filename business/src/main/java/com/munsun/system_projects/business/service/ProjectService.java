package com.munsun.system_projects.business.service;

import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.ProjectDtoIn;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;

import java.util.List;

public interface ProjectService {
    ProjectDtoOut createProject(ProjectDtoIn projectDTO) throws ProjectDuplicateException, ProjectEmptyFieldsException;
    ProjectDtoOut setProject(int id, ProjectDtoIn projectDTO) throws ProjectDuplicateException, ProjectNotFoundException, ProjectEmptyFieldsException;
    List<ProjectDtoOut> getProjectsByStringAndStatuses(String str, StatusProject ...statusProjects);
    ProjectDtoOut getProjectById(int id) throws ProjectNotFoundException;
    ProjectDtoOut updateStatusProject(int id, StatusProject status) throws ProjectNotFoundException, ProjectIncorrectStatusUpdateException, ProjectEmptyFieldsException;
}
