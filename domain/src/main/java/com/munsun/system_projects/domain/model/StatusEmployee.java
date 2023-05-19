package com.munsun.system_projects.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatusEmployee {
    private int id;
    private String name;

    public StatusEmployee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StatusEmployee() {
    }
}
