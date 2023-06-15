package com.munsun.system_projects.commons.exceptions;

public class EmployeeEmptyFieldsException extends Exception {
    public EmployeeEmptyFieldsException() {
        super("Обязательные поля-имя, фамилия, должность, аккаунт не заполнены");
    }
}
