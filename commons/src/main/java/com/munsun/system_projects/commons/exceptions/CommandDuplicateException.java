package com.munsun.system_projects.commons.exceptions;

public class CommandDuplicateException extends Exception {
    public CommandDuplicateException() {
        super("Такая команда уже существует");
    }
}
