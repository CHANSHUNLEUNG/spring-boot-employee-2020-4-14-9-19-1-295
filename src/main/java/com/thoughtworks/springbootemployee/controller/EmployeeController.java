package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(1, "leo1", 18, "male"));
        employees.add(new Employee(2, "leo2", 18, "male"));
        employees.add(new Employee(3, "leo3", 18, "male"));
        employees.add(new Employee(4, "leo4", 18, "male"));
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployees(@RequestBody Employee newEmployee) {
        employees.add(newEmployee);
        return newEmployee;
    }

    @PutMapping
    public ResponseEntity<Object> updateEmployees(@RequestBody Employee newEmployee) {
        Employee targetEmployee = this.employees.stream()
                .filter(employee -> employee.getId() == newEmployee.getId())
                .findFirst()
                .orElse(null);
        if (targetEmployee == null) {
            return new ResponseEntity<>("Error,Employee does not exist", HttpStatus.BAD_REQUEST);
        }
        employees.set(employees.indexOf(targetEmployee), newEmployee);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{employeeID}")
    public ResponseEntity<Object> updateEmployees(@PathVariable int employeeID) {
        Employee targetEmployee = this.employees.stream()
                .filter(employee -> employee.getId() == employeeID)
                .findFirst()
                .orElse(null);
        if (targetEmployee == null) {
            return new ResponseEntity<>("Error,Employee does not exist", HttpStatus.BAD_REQUEST);
        }
        employees.remove(targetEmployee);
        return new ResponseEntity<>("Remove employee with id " + employeeID + " successfully", HttpStatus.OK);
    }

}
