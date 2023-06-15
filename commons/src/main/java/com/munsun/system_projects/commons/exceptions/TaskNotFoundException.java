package com.munsun.system_projects.commons.exceptions;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super("Задачи не существует");
    }
}
