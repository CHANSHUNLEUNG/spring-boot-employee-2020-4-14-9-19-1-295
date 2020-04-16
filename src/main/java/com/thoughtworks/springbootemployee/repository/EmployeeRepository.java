package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void delete(Employee existingEmployee) {
        this.employees.remove(existingEmployee);
    }

    public List<Employee> findByGender(String gender) {
        return this.employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }
}
