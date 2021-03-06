package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployees(@RequestBody Employee newEmployee) {
        return employeeService.createEmployees(newEmployee);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<Object> updateEmployees(@PathVariable int employeeId, @RequestBody Employee newEmployee) {
        employeeService.updateEmployees(employeeId, newEmployee);
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
        List<Employee> returnEmployees = employeeService.getEmployeeByGender(gender);
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getEmployees
            (@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        List<Employee> returnEmployees = employeeService.getEmployeesWithPagination(page, pageSize);
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
    }

}
