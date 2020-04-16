package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public EmployeeService employeeService;

    public void setEmployees(List<Employee> employees) {
        employeeService.setEmployees(employees);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployees(@RequestBody Employee newEmployee) {
        return employeeService.createEmployees(newEmployee);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<Object> updateEmployees(@PathVariable int employeeId, @RequestBody Employee newEmployee) {
        employeeService.updateEmployees(employeeId,newEmployee);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Object> deleteEmployees(@PathVariable int employeeId) {
        employeeService.deleteEmployees(employeeId);
        return new ResponseEntity<>("Remove employee with id " + employeeId + " successfully", HttpStatus.OK);
    }

    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<Object> getEmployees(@PathVariable int employeeID) {
        Employee targetEmployee = employeeService.getEmployeeById(employeeID);
        if (targetEmployee == null) {
            return new ResponseEntity<>("Error, employee does not exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(targetEmployee, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"gender"})
    public ResponseEntity<Object> getEmployees(@RequestParam(value = "gender") String gender) {
        List<Employee> returnEmployees = this.employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
    }

    @GetMapping(params = {"page", "pageSize"})
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
