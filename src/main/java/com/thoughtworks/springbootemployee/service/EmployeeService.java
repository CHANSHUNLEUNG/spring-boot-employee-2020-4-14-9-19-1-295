package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployees(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public void updateEmployees(int employeeId, Employee newEmployee) {
        Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existingEmployee == null) {
            return;
        }
        existingEmployee.setName(newEmployee.getName());
        existingEmployee.setAge(newEmployee.getAge());
        existingEmployee.setCompanyId(newEmployee.getCompanyId());
        existingEmployee.setGender(newEmployee.getGender());
        existingEmployee.setSalary(newEmployee.getSalary());

        employeeRepository.save(existingEmployee);
    }

    public void deleteEmployees(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getEmployeesWithPagination(int page, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }
}
