package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeController(List<Employee> employees) {
        this.employees.add(new Employee(1, "leo1", 18, "male", 80000));
        this.employees.add(new Employee(2, "leo2", 18, "male", 80000));
        this.employees.add(new Employee(3, "leo3", 18, "male", 80000));
        this.employees.add(new Employee(4, "leo4", 18, "male", 80000));
        this.employees.add(new Employee(5, "leo5", 18, "male", 80000));
        this.employees.add(new Employee(6, "leo6", 18, "male", 80000));
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return this.employees;
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

    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<Object> getEmployees(@PathVariable int employeeID) {
        Employee targetEmployee = this.employees.stream()
                .filter(employee -> employee.getId() == employeeID)
                .findFirst()
                .orElse(null);
        if (targetEmployee == null) {
            return new ResponseEntity<>("Error,Employee does not exist", HttpStatus.BAD_REQUEST);
        }
        employees.remove(targetEmployee);
        return new ResponseEntity<>(targetEmployee, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"gender"})
    public ResponseEntity<Object> getEmployees(@RequestParam(value = "gender") String gender) {
        List<Employee> returnEmployees = this.employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"page", "pageSize"})
    public ResponseEntity<Object> getEmployees
            (@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize;
        if (this.employees.size() < startIndex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } else if (this.employees.size() > startIndex && this.employees.size() < endIndex) {
            return new ResponseEntity<>(this.employees.subList(startIndex, employees.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.employees.subList((page - 1) * pageSize, page * pageSize), HttpStatus.OK);

    }


}
