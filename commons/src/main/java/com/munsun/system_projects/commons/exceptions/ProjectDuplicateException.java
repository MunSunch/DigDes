package com.munsun.system_projects.commons.exceptions;

public class ProjectDuplicateException extends Exception {
    public ProjectDuplicateException() {
        super("Проект уже существует!");
    }
}
