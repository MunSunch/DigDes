package com.munsun.system_projects.commons.exceptions;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException() {
        super("Команда не найдена");
    }
}
