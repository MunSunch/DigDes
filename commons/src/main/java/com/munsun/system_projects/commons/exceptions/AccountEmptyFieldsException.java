package com.munsun.system_projects.commons.exceptions;

public class AccountEmptyFieldsException extends Exception {
    public AccountEmptyFieldsException() {
        super("Обязательные поля учетной записи не заполнены");
    }
}
