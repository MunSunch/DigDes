package com.munsun.system_projects.business.integ;

import com.munsun.system_projects.business.Container;
import com.munsun.system_projects.business.service.ProjectService;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.commons.exceptions.ProjectDuplicateException;
import com.munsun.system_projects.commons.exceptions.ProjectEmptyFieldsException;
import com.munsun.system_projects.commons.exceptions.ProjectIncorrectStatusUpdateException;
import com.munsun.system_projects.commons.exceptions.ProjectNotFoundException;
import com.munsun.system_projects.dto.entity.in.ProjectDtoIn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProjectServiceIntegTest extends Container {
    @Autowired
    private ProjectService service;

    @Test
    public void testCreateNotExistsProject() throws ProjectEmptyFieldsException, ProjectDuplicateException, ProjectNotFoundException {
        ProjectDtoIn test = new ProjectDtoIn();
        test.setName("test");
        test.setDescription("test");

        var emp = service.createProject(test);
        var actual = service.getProjectById(emp.getId());

        assertAll(
                ()->assertNotEquals(0, actual.getId()),
                ()->assertNotEquals(null, actual.getCode()),
                ()->assertEquals(test.getName(), actual.getName()),
                ()->assertEquals(test.getDescription(), actual.getDescription()),
                ()->assertEquals(StatusProject.DRAFT, actual.getStatus())
        );
    }

    @Test
    public void testCreateExistsProject() throws ProjectEmptyFieldsException, ProjectDuplicateException {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        ProjectDtoIn test2 = new ProjectDtoIn();
        test2.setName("test1");
        test2.setDescription("test2");

        service.createProject(test1);

        assertThrowsExactly(ProjectDuplicateException.class, ()->{
            service.createProject(test2);
        });
    }

    @Test
    public void testSetProject() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        ProjectDtoIn test2 = new ProjectDtoIn();
        test2.setName("test2");
        test2.setDescription("test2");

        var emp = service.createProject(test1);
        service.setProject(emp.getId(), test2);
        var actual = service.getProjectById(emp.getId());

        assertAll(
                () -> assertEquals(emp.getId(), actual.getId()),
                () -> assertEquals(emp.getCode(), actual.getCode()),
                () -> assertEquals(test2.getDescription(), actual.getDescription()),
                () -> assertEquals(test2.getName(), actual.getName()),
                () -> assertEquals(StatusProject.DRAFT, actual.getStatus())
        );
    }

    @Test
    public void testSetNotExistsProject() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        assertThrowsExactly(ProjectNotFoundException.class, ()->{
            service.setProject(100, test1);
        });
    }

    @Test
    public void testGetProjectById() throws ProjectEmptyFieldsException, ProjectDuplicateException, ProjectNotFoundException {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        int id = service.createProject(test1).getId();
        var actual = service.getProjectById(id);

        assertAll(
                ()->assertNotEquals(0, actual.getId()),
                ()->assertNotEquals(null, actual.getCode()),
                ()->assertEquals(test1.getName(), actual.getName()),
                ()->assertEquals(test1.getDescription(), actual.getDescription()),
                ()->assertEquals(StatusProject.DRAFT, actual.getStatus())
        );
    }

    @Test
    public void testGetProjectByNotExistsId() throws ProjectEmptyFieldsException, ProjectDuplicateException, ProjectNotFoundException {
        assertThrowsExactly(ProjectNotFoundException.class, ()->{
            service.getProjectById(100);
        });
    }

    @ParameterizedTest
    @EnumSource(StatusProject.class)
    public void positiveTestUpdateStatusProject(StatusProject status) throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");
        var emp = service.createProject(test1);

        service.updateStatusProject(emp.getId(), status);
        var actual = service.getProjectById(emp.getId()).getStatus();

        assertEquals(status, actual);
    }

    @Test
    public void negativeTestUpdateStatusProject() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");
        var emp = service.createProject(test1);
        service.updateStatusProject(emp.getId(), StatusProject.TESTING);

        assertThrowsExactly(ProjectIncorrectStatusUpdateException.class, ()-> {
            service.updateStatusProject(emp.getId(), StatusProject.DEVELOPING);
        });
    }

    @Test
    public void testGetProjectsByCodeAndAllStatus() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        ProjectDtoIn test2 = new ProjectDtoIn();
        test2.setName("test2");
        test2.setDescription("test2");

        service.createProject(test1);
        var emp = service.createProject(test2);
        var actual = service.getProjectsByStringAndStatuses(emp.getCode());

        assertEquals(1, actual.size());
        assertAll(
                () -> assertEquals(emp.getId(), actual.get(0).getId()),
                () -> assertEquals(emp.getCode(), actual.get(0).getCode()),
                () -> assertEquals(test2.getDescription(), actual.get(0).getDescription()),
                () -> assertEquals(test2.getName(), actual.get(0).getName()),
                () -> assertEquals(StatusProject.DRAFT, actual.get(0).getStatus())
        );
    }

    @Test
    public void testGetProjectsByNameAndAllStatus() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        ProjectDtoIn test2 = new ProjectDtoIn();
        test2.setName("testName");
        test2.setDescription("test2");

        service.createProject(test1);
        var emp = service.createProject(test2);
        var actual = service.getProjectsByStringAndStatuses(emp.getName());

        assertEquals(1, actual.size());
        assertAll(
                () -> assertEquals(emp.getId(), actual.get(0).getId()),
                () -> assertEquals(emp.getCode(), actual.get(0).getCode()),
                () -> assertEquals(test2.getDescription(), actual.get(0).getDescription()),
                () -> assertEquals(test2.getName(), actual.get(0).getName()),
                () -> assertEquals(StatusProject.DRAFT, actual.get(0).getStatus())
        );
    }

    @Test
    public void testGetProjectsByDescriptionAndAllStatus() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        ProjectDtoIn test2 = new ProjectDtoIn();
        test2.setName("test2");
        test2.setDescription("testDescription");

        service.createProject(test1);
        var emp = service.createProject(test2);
        var actual = service.getProjectsByStringAndStatuses(emp.getDescription());

        assertEquals(1, actual.size());
        assertAll(
                () -> assertEquals(emp.getId(), actual.get(0).getId()),
                () -> assertEquals(emp.getCode(), actual.get(0).getCode()),
                () -> assertEquals(test2.getDescription(), actual.get(0).getDescription()),
                () -> assertEquals(test2.getName(), actual.get(0).getName()),
                () -> assertEquals(StatusProject.DRAFT, actual.get(0).getStatus())
        );
    }

    @Test
    public void testGetProjectsByDescriptionAndDevelopStatus() throws Exception {
        ProjectDtoIn test1 = new ProjectDtoIn();
        test1.setName("test1");
        test1.setDescription("test1");

        ProjectDtoIn test2 = new ProjectDtoIn();
        test2.setName("test2");
        test2.setDescription("testDescription");

        service.createProject(test1);
        var emp = service.createProject(test2);
        service.updateStatusProject(emp.getId(), StatusProject.DEVELOPING);

        var actual = service.getProjectsByStringAndStatuses(emp.getDescription(),
                StatusProject.DEVELOPING);

        assertEquals(1, actual.size());
        assertAll(
                () -> assertEquals(emp.getId(), actual.get(0).getId()),
                () -> assertEquals(emp.getCode(), actual.get(0).getCode()),
                () -> assertEquals(test2.getDescription(), actual.get(0).getDescription()),
                () -> assertEquals(test2.getName(), actual.get(0).getName()),
                () -> assertEquals(StatusProject.DEVELOPING, actual.get(0).getStatus())
        );
    }
}










