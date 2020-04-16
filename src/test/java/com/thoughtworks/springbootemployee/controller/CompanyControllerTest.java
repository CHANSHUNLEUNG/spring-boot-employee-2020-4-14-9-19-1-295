package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    public CompanyController companyController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(companyController);

        this.companyController.setCompanies(new ArrayList<>(Arrays.asList(
                new Company(1, "leocompany1"),
                new Company(2, "leocompany2"),
                new Company(3, "leocompany3")
        )));
        this.companyController.getCompanies().get(1).setEmployees(new ArrayList<>(Arrays.asList(
                new Employee(1, "leo1", 18, "male", 80000),
                new Employee(2, "leo2", 18, "male", 80000),
                new Employee(3, "leo3", 18, "male", 80000)
        )));
    }

    @Test
    public void should_return_all_companies() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");

        List<Company> companies = mvcResponse.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(3, this.companyController.getCompanies().size());
        Assert.assertEquals(2, this.companyController.getCompanies().get(1).getId());
        Assert.assertEquals("leocompany2", this.companyController.getCompanies().get(1).getName());
    }

    @Test
    public void should_return_specified_company_when_given_employeeId() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/2");

        Company company = mvcResponse.getBody().as(Company.class);

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, company.getId());
        Assert.assertEquals("leocompany2", company.getName());
    }

    @Test
    public void should_return_all_employees_of_a_company_when_given_companyId() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/2/employees");

        List<Employee> employees = mvcResponse.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals(1, employees.get(0).getId());
        Assert.assertEquals("leo1", employees.get(0).getName());
    }

    @Test
    public void should_return_1_company_when_get_companies_given_page_is_2_and_pageSize_is_2() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .params(new HashMap<String, Integer>() {{
                    put("page", 2);
                    put("pageSize", 2);
                }})
                .when()
                .get("/companies");
        List<Company> Companies = mvcResponse.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(1, Companies.size());
    }

    @Test
    public void should_add_new_company_successfully_when_given_new_company() {
        Company newCompany = new Company(4, "leocompany4");

        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post("/companies");

        Company company = mvcResponse.getBody().as(Company.class);
        Assert.assertEquals(HttpStatus.CREATED, mvcResponse.getStatusCode());
        Assert.assertEquals(4, this.companyController.getCompanies().size());
        Assert.assertEquals(4, this.companyController.getCompanies().get(3).getId());
        Assert.assertEquals("leocompany4", this.companyController.getCompanies().get(3).getName());
    }

    @Test
    public void should_update_company_successfully_when_given_existing_company() {
        Company newCompany = new Company(2, "leocompany20");

        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .put("/companies/2");

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, this.companyController.getCompanies().get(1).getId());
        Assert.assertEquals("leocompany20", this.companyController.getCompanies().get(1).getName());
    }
}