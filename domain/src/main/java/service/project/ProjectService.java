package service.project;

import model.project.Project;
import model.project.StatusProject;

public interface ProjectService {
    void saveProject(Project project);
    void setProject(Project oldProject, Project newProject);
    Project findProject(String value, StatusProject... status);
    void setStatusProject(Project project);
}
