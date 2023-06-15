package com.munsun.system_projects.commons.exceptions;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException() {
        super("Сотрудник не найден");
    }
}
