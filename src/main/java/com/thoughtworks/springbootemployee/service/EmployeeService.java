package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployees(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public boolean updateEmployees(int employeeId, Employee newEmployee) {
        Employee existingEmployee = employeeRepository.findById(new Integer(employeeId)).orElse(null);
        if (existingEmployee == null) {
            return false;
        }
        employeeRepository.save(existingEmployee);
        return true;
    }

    public boolean deleteEmployees(int employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existingEmployee == null) {
            return false;
        }
        employeeRepository.delete(existingEmployee);
        return true;
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(new Integer(employeeId)).orElse(null);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
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
