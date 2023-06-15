package com.munsun.system_projects.commons.exceptions;

public class CommandEmployeeNotFoundException extends Exception {
    public CommandEmployeeNotFoundException(String message) {
        super("Сотрудник в команде не найден: "+message);
    }
}
