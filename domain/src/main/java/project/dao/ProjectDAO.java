package project.dao;

import project.model.Project;
import project.model.StatusProject;

public interface ProjectDAO {
    void createProject(Project newProject);
    void setProject(Project oldProject, Project newProject);
    Project findProject(String value, StatusProject... status);
    void setStatusProject(Project project);
}
