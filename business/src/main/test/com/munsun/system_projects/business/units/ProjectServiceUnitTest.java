package com.munsun.system_projects.business.units;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.repository.ProjectRepository;
import com.munsun.system_projects.business.repository.StatusProjectRepository;
import com.munsun.system_projects.business.service.ProjectService;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.commons.exceptions.ProjectDuplicateException;
import com.munsun.system_projects.commons.exceptions.ProjectEmptyFieldsException;
import com.munsun.system_projects.dto.entity.in.ProjectDtoIn;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProjectServiceUnitTest {
    @MockBean
    private  ProjectRepository projectRepository;
    @MockBean
    private  StatusProjectRepository statusProjectRepository;
    @SpyBean
    private  Mapper<Project, ProjectDtoOut> projectMapper;

    @Autowired
    private ProjectService service;

    @Test
    public void testCreateProjectEmptyName() throws ProjectEmptyFieldsException, ProjectDuplicateException {
        ProjectDtoIn test = new ProjectDtoIn();
        test.setName("");
        test.setDescription("test");

        assertThrowsExactly(ProjectEmptyFieldsException.class, ()-> {
            service.createProject(test);
        });
    }

    @Test
    public void testCreateProjectNullName() throws ProjectEmptyFieldsException, ProjectDuplicateException {
        ProjectDtoIn test = new ProjectDtoIn();
        test.setName(null);
        test.setDescription("test");

        assertThrowsExactly(ProjectEmptyFieldsException.class, ()-> {
            service.createProject(test);
        });
    }

    @Test
    public void testGetProjectByEmptyString() {
        String str = "";

        assertThrowsExactly(IllegalArgumentException.class, ()->{
            service.getProjectsByStringAndStatuses(str, StatusProject.DRAFT);
        });
    }

    @Test
    public void testUpdateStatusProjectNullStatus() {
        when(projectRepository.existsById(anyInt())).thenReturn(true);

        assertThrowsExactly(ProjectEmptyFieldsException.class, ()->{
            service.updateStatusProject(anyInt(), null);
        });
    }
}
