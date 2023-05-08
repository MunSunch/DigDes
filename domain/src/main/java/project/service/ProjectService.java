package project.service;

import project.model.Project;
import project.model.StatusProject;

public interface ProjectService {
    void saveProject(Project project);
    void setProject(Project oldProject, Project newProject);
    Project findProject(String value, StatusProject... status);
    void setStatusProject(Project project);
}
