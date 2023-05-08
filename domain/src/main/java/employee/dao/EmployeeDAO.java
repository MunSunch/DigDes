package employee.dao;

import employee.model.Employee;

public interface EmployeeDAO {
    void createEmployee(Employee newEmployee);
    void setEmployee(Employee oldEmployee, Employee newEmployee);
    Employee removeEmployee(int id);
    Employee findEmployee(String value);
}