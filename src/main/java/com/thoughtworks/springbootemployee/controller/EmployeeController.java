package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();
    @GetMapping
    public List<Employee> getEmployees(){
        employees.add(new Employee(1,"leo1",18, "male"));
        employees.add(new Employee(2,"leo2",18, "male"));
        employees.add(new Employee(3,"leo3",18, "male"));
        employees.add(new Employee(4,"leo4",18, "male"));
        return employees;
    }


}
