package com.munsun.system_projects.commons.exceptions;

public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException() {
        super("Проекта не существует!");
    }
}
