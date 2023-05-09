package service.employee;

import model.employee.Employee;

public interface EmployeeService {
    void saveEmployee(Employee newEmployee);
    void setEmployee(Employee oldEmployee, Employee newEmployee);
    Employee removeEmployee(int id);
    Employee findEmployee(String value);
}
