package employee.service;

import employee.model.Employee;

public interface EmployeeService {
    void saveEmployee(Employee newEmployee);
    void setEmployee(Employee oldEmployee, Employee newEmployee);
    Employee removeEmployee(int id);
    Employee findEmployee(String value);
}
