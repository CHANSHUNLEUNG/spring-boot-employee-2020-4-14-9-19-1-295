package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

    EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Test
    public void should_return_all_employees_successfully() {
        employeeService.getEmployees();

        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void should_return_specified_employee_when_given_employeeID() {
        employeeService.getEmployeeById(2);

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(2);
    }

    @Test
    public void should_add_employee_successfully_when_given_an_employee() {
        Employee newEmployee = new Employee(4, "leo4", 18, "male", 80000, 1);

        employeeService.createEmployees(newEmployee);

        Mockito.verify(employeeRepository, Mockito.times(1)).save(newEmployee);
    }

    @Test
    public void should_delete_employee_successfully_when_given_an_employee() {
        employeeService.deleteEmployees(2);

        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(2);
    }

    @Test
    public void should_update_employee_successfully_when_given_existing_employee() {
        Employee oldEmployee = new Employee(1, "leo1", 18, "male", 100000, 1);
        Employee newEmployee = new Employee(1, "leo1", 20, "male", 100000, 1);
        oldEmployee.setAge(newEmployee.getAge());

        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(oldEmployee));

        employeeService.updateEmployees(1, newEmployee);

        Mockito.verify(employeeRepository, Mockito.times(1)).save(oldEmployee);
    }

    @Test
    public void should_return_all_male_employees_when_given_gender_is_male() {
        employeeService.getEmployeeByGender("male");

        Mockito.verify(employeeRepository, Mockito.times(1)).findAllByGender("male");
    }

    @Test
    public void should_return_1_employee_when_get_employees_given_page_is_2_and_pageSize_is_2() {
        int page = 2;
        int pageSize = 2;

        Page<Employee> pagedCompanies = Mockito.mock(Page.class);
        Mockito.when(employeeRepository.findAll(PageRequest.of(page, pageSize))).thenReturn(pagedCompanies);
        Mockito.when(pagedCompanies.getContent()).thenReturn(null);

        employeeService.getEmployeesWithPagination(page, pageSize);

        Mockito.verify(employeeRepository, Mockito.times(1))
                .findAll(PageRequest.of(page, pageSize));
    }
}
