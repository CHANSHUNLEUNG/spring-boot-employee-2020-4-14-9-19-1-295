package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return this.employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee save(Employee newEmployee) {
        this.employees.add(newEmployee);
        return newEmployee;
    }

    public Employee findByID(int employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    public void update(Employee existingEmployee, Employee newEmployee) {
        this.employees.set(employees.indexOf(existingEmployee), newEmployee);
    }
}
