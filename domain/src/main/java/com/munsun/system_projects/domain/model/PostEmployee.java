package com.munsun.system_projects.domain.model;

import lombok.Data;

@Data
public class PostEmployee {
    private int id;
    private String name;

    public PostEmployee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PostEmployee() {}
}
