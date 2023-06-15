package com.munsun.system_projects.commons.exceptions;

public class CommandEmployeeDuplicateException extends Exception {
    public CommandEmployeeDuplicateException() {
        super("Сотрудник в данной команде уже существует");
    }
}
