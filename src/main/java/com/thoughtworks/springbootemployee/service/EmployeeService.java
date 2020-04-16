package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void setEmployees(List<Employee> employees) {
        employeeRepository.setEmployees(employees);
    }

    public Employee createEmployees(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public void updateEmployees(int employeeId, Employee newEmployee) {
        Employee existingEmployee = employeeRepository.findByID(employeeId);
        if (existingEmployee != null) {
            employeeRepository.update(existingEmployee, newEmployee);
        }
    }

    public void deleteEmployees(int employeeId) {
        Employee existingEmployee = employeeRepository.findByID(employeeId);
        if(existingEmployee != null) {
            employeeRepository.delete(existingEmployee);
        }
    }

    public Employee getEmployeeById(int employeeID) {
        return employeeRepository.findByID(employeeID);
    }
}
