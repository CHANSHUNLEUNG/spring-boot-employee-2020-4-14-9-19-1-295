package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CompanyRepository companyRepository;



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

//        this.employeeRepository = Mockito.mock(EmployeeRepository.class);
//        this.employeeService = new EmployeeService(this.employeeRepository);
//
//        this.companyRepository = Mockito.mock(CompanyRepository.class);


//        companyRepository.save(new Company(1, "leocompany1", 0, null));
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

        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");

        Mockito.verify(employeeRepository, Mockito.times(1)).save(newEmployee);

    }

    @Test
    public void should_delete_employee_successfully_when_given_an_employee() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/2");

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, this.employeeController.getEmployees().size());
    }

    @Test
    public void should_update_employee_successfully_when_given_existing_employee() {
        Employee employee = new Employee(1, "leo1", 18, "male", 100000, 1);

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
                .params("gender", "male")
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
        Assert.assertEquals("leo3", employees.get(1).getName());
    }

    @Test
    public void should_return_1_employee_when_get_employees_given_page_is_2_and_pageSize_is_2() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .params(new HashMap<String, Integer>() {{
                    put("page", 2);
                    put("pageSize", 2);
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
        Assert.assertEquals(1, employees.size());
    }
}
