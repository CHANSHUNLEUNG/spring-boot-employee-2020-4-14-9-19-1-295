package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CompanyRepository companyRepository;

    EmployeeRepository mockEmployeeRepository = Mockito.mock(EmployeeRepository.class);
    EmployeeService mockEmployeeService = new EmployeeService(mockEmployeeRepository);

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(employeeController);

        Optional<Employee> employee1 =
                Optional.of(new Employee(1, "leo1", 18, "male", 80000, 1));
        Optional<Employee> employee2 =
                Optional.of(new Employee(2, "leo2", 18, "female", 80000, 1));
        Optional<Employee> employee3 =
                Optional.of(new Employee(3, "leo3", 18, "male", 80000, 1));

        List<Employee> mockEmployees = new ArrayList<>(Arrays.asList(
                employee1.get(),
                employee2.get(),
                employee3.get()
        ));

        Mockito.when(employeeRepository.findAll()).thenReturn(mockEmployees);
        Mockito.when(employeeRepository.findById(2)).thenReturn(employee2);
    }


    @Test
    public void should_return_all_employees_successfully() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        List<Employee> employees = mvcResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals("leo2", employees.get(1).getName());
    }

    @Test
    public void should_return_specified_employee_when_given_employeeID() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/2");

        Employee employee = mvcResponse.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals("leo2", employee.getName());
    }

    @Test
    public void should_add_employee_successfully_when_given_an_employee() {
        Employee newEmployee = new Employee(4, "leo4", 18, "male", 80000, 1);

        mockEmployeeService.createEmployees(newEmployee);

        Mockito.verify(mockEmployeeRepository, Mockito.times(1)).save(newEmployee);
    }

    @Test
    public void should_delete_employee_successfully_when_given_an_employee() {
        mockEmployeeService.deleteEmployees(2);

        Mockito.verify(mockEmployeeRepository, Mockito.times(1)).deleteById(2);
    }

    @Test
    public void should_update_employee_successfully_when_given_existing_employee() {
        Employee oldEmployee = new Employee(1, "leo1", 18, "male", 100000, 1);
        Employee newEmployee = new Employee(1, "leo1", 20, "male", 100000, 1);
        oldEmployee.setAge(newEmployee.getAge());

        Mockito.when(mockEmployeeRepository.findById(1)).thenReturn(Optional.of(oldEmployee));

        mockEmployeeService.updateEmployees(1, newEmployee);

        Mockito.verify(mockEmployeeRepository, Mockito.times(1)).save(oldEmployee);
    }

    @Test
    public void should_return_all_male_employees_when_given_gender_is_male() {
        mockEmployeeService.getEmployeeByGender("male");

        Mockito.verify(mockEmployeeRepository, Mockito.times(1)).findAllByGender("male");
    }

    @Test
    public void should_return_1_employee_when_get_employees_given_page_is_2_and_pageSize_is_2() {
        int page = 2;
        int pageSize = 2;

        Page<Employee> pagedCompanies = Mockito.mock(Page.class);
        Mockito.when(mockEmployeeRepository.findAll(PageRequest.of(2, 2))).thenReturn(pagedCompanies);
        Mockito.when(pagedCompanies.getContent()).thenReturn(null);

        mockEmployeeService.getEmployeesWithPagination(page, pageSize);

        Mockito.verify(mockEmployeeRepository, Mockito.times(1))
                .findAll(PageRequest.of(page, pageSize));
    }
}
