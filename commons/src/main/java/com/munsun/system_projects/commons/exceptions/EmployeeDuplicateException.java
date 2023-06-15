package com.munsun.system_projects.commons.exceptions;

public class EmployeeDuplicateException extends Exception {
    public EmployeeDuplicateException() {
        super("Сотрудник уже существует");
    }
}
