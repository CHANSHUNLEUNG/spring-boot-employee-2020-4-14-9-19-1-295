package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.entity.Employee;
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
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
        employeeController.getEmployees().add(new Employee(1,"leo1",18,"male",80000));
        employeeController.getEmployees().add(new Employee(2,"leo2",18,"male",80000));
        employeeController.getEmployees().add(new Employee(3,"leo3",18,"male",80000));
    }

    @Test
    public void should_get_all_employees() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");
        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        List<Employee> employees = mvcResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3,employees.size());
        Assert.assertEquals(2,employees.get(1).getId());
        Assert.assertEquals("leo2",employees.get(1).getName());
    }
}
