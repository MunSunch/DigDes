package com.munsun.system_projects.commons.exceptions;

public class AccountDuplicateException extends Exception{
    public AccountDuplicateException() {
        super("Учетная запись уже существует");
    }
}
