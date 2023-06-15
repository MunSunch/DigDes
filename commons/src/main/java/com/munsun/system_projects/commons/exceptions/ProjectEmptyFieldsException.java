package com.munsun.system_projects.commons.exceptions;

public class ProjectEmptyFieldsException extends Exception {
    public ProjectEmptyFieldsException() {
        super("Обязательные поля проекта не заполнены");
    }
}
