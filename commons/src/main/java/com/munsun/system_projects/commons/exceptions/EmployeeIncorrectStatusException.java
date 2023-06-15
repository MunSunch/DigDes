package com.munsun.system_projects.commons.exceptions;

public class EmployeeIncorrectStatusException extends Exception {
    public EmployeeIncorrectStatusException() {
        super("Статус должен быть ACTIVE");
    }
}
