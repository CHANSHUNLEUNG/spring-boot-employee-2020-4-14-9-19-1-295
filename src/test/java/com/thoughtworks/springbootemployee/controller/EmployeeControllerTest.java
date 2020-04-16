package com.thoughtworks.springbootemployee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(employeeController);

        this.employeeController.setEmployees(new ArrayList<>(Arrays.asList(
                new Employee(1, "leo1", 18, "male", 80000),
                new Employee(2, "leo2", 18, "female", 80000),
                new Employee(3, "leo3", 18, "male", 80000)
        )));
    }

    @Test
    public void should_return_all_employees_successfully() {
        //when
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        List<Employee> employees = mvcResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        //then
        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals(2, employees.get(1).getId());
        Assert.assertEquals("leo2", employees.get(1).getName());
    }

    @Test
    public void should_return_specified_employee_when_given_employeeID() {
        //when
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/2");

        Employee employee = mvcResponse.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, employee.getId());
        Assert.assertEquals("leo2", employee.getName());
    }

    @Test
    public void should_add_employee_successfully_when_given_an_employee() throws JsonProcessingException {
        //given
        Employee newEmployee = new Employee(4, "leo4", 18, "male", 80000);
        //when
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");

        Employee employee = mvcResponse.getBody().as(Employee.class);

        Assert.assertEquals(HttpStatus.CREATED, mvcResponse.getStatusCode());
        Assert.assertEquals(4, this.employeeController.getEmployees().get(3).getId());
        Assert.assertEquals("leo4", this.employeeController.getEmployees().get(3).getName());
    }

    @Test
    public void should_delete_employee_successfully_when_given_an_employee() {
        //when
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/2");

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, this.employeeController.getEmployees().size());
    }

    @Test
    public void should_update_employee_successfully_when_given_existing_employee() {
        //when
        Employee employee = new Employee(1, "leo1", 18, "male", 100000);

        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .put("/employees/1");

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(100000, this.employeeController.getEmployees().get(0).getSalary());
    }

    @Test
    public void should_return_all_male_employees_when_given_gender_is_male() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .params("gender","male")
                .when()
                .get("/employees");

        List<Employee> employees = mvcResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals(3, employees.get(1).getId());
    }

    @Test
    public void should_return_one_employee_when_given_page_is_2_and_pageSize_is_2() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .params(new HashMap<String, Integer>(){{
                    put("page",2);
                    put("pageSize",2);
                }})
                .when()
                .get("/employees");
        List<Employee> employees = mvcResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(1,employees.size());
    }
}
