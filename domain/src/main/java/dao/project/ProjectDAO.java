package dao.project;

import model.project.Project;
import model.project.StatusProject;

public interface ProjectDAO {
    void createProject(Project newProject);
    void setProject(Project oldProject, Project newProject);
    Project findProject(String value, StatusProject... status);
    void setStatusProject(Project project);
}
