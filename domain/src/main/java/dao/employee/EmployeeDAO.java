package dao.employee;

import model.employee.Employee;

public interface EmployeeDAO {
    void createEmployee(Employee newEmployee);
    void setEmployee(Employee oldEmployee, Employee newEmployee);
    Employee removeEmployee(int id);
    Employee findEmployee(String value);
}