package com.munsun.system_projects.domain.model;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String login;
    private String password;

    public Account(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account() {
    }
}
