package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public boolean updateEmployees(int employeeId, Employee newEmployee) {
        Employee existingEmployee = employeeRepository.findByID(employeeId);
        if (existingEmployee == null) {
            return false;
        }
        employeeRepository.update(existingEmployee, newEmployee);
        return true;
    }

    public boolean deleteEmployees(int employeeId) {
        Employee existingEmployee = employeeRepository.findByID(employeeId);
        if (existingEmployee == null) {
            return false;
        }
        employeeRepository.delete(existingEmployee);
        return true;
    }

    public Employee getEmployeeById(int employeeID) {
        return employeeRepository.findByID(employeeID);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getEmployeesWithPagination(int page, int pageSize) {
        List<Employee> employees = employeeRepository.findAll();
        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize;
        if (employees.size() < startIndex) {
            return new ArrayList<>();
        } else if (employees.size() > startIndex && employees.size() < endIndex) {
            return employees.subList(startIndex, employees.size());
        }
        return employees.subList((page - 1) * pageSize, page * pageSize);
    }
}
