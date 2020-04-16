package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
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
        ;

        this.companyController.setCompanies(new ArrayList<>(Arrays.asList(
                new Company(1, "leocompany1"),
                new Company(2, "leocompany2"),
                new Company(3, "leocompany3")
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
    public void should_return_specified_company_when_given_employeeID() {
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/2");

        Company company = mvcResponse.getBody().as(Company.class);

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, company.getId());
        Assert.assertEquals("leocompany2", company.getName());
    }
}