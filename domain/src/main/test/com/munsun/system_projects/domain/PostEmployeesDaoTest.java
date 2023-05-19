package com.munsun.system_projects.domain;

import com.munsun.system_projects.domain.dao.impl.PostEmployeeDAO;
import com.munsun.system_projects.domain.model.PostEmployee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PostEmployeesDaoTest {
    private static PostEmployeeDAO postEmployeeDAO;

    @BeforeAll
    public static void setPostEmployee() {
        postEmployeeDAO = new PostEmployeeDAO("jdbc:postgresql://localhost:5432/system_projects_db",
                "postgres", "4669");
    }

    @Test
    public void testGetAll() {
        List<PostEmployee> expected = new ArrayList<>();
        expected.add(new PostEmployee(1, "cleaner"));
        expected.add(new PostEmployee(2, "assistant"));
        expected.add(new PostEmployee(3, "developer"));
        expected.add(new PostEmployee(4, "generator"));

        var actual = postEmployeeDAO.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        PostEmployee expected = new PostEmployee(2, "assistant");

        var actual = postEmployeeDAO.getById(2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSave() {
        int expected = 1;
        PostEmployee testPostEmployee = new PostEmployee("testPost");

        var actual = postEmployeeDAO.save(testPostEmployee);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        int expected = 1;
        PostEmployee testPostEmployee = new PostEmployee("testPost");

        var actual = postEmployeeDAO.delete(testPostEmployee);

        Assertions.assertEquals(expected, actual);
    }
}
