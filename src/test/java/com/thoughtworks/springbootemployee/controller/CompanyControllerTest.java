package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    private CompanyController oldController;

    @Autowired
    private CompanyRepository oldrepo;

    private CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);

    private CompanyService companyService = new CompanyService(companyRepository);


    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(oldController);
    }

    @Test
    public void should_return_all_companies() {
        companyService.getCompanies();

        Mockito.verify(companyRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void should_return_specified_company_when_given_employeeId() {
        companyService.getCompanyById(2);

        Mockito.verify(companyRepository, Mockito.times(1)).findById(2);
    }

    @Test
    public void should_return_all_employees_of_a_company_when_given_companyId() {
        companyService.getCompanyById(2);

        Mockito.verify(companyRepository, Mockito.times(1)).findById(2);
    }

    @Test
    public void should_return_1_company_when_get_companies_given_page_is_2_and_pageSize_is_2() {
        int page = 2;
        int pageSize = 2;

        Page<Company> pagedCompanies = Mockito.mock(Page.class);
        Mockito.when(companyRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pagedCompanies);
        Mockito.when(pagedCompanies.getContent()).thenReturn(null);

        companyService.getCompaniesWithPagination(page, pageSize);

        Mockito.verify(companyRepository, Mockito.times(1))
                .findAll(PageRequest.of(page, pageSize));
    }

    @Test
    public void should_add_new_company_successfully_when_given_new_company() {
        Company newCompany = new Company(1, "leocompany1", 0, new ArrayList<>());

        companyService.createCompanies(newCompany);

        Mockito.verify(companyRepository, Mockito.times(1)).save(Mockito.any(Company.class));
    }

    @Test
    public void should_update_company_successfully_when_given_existing_company() {
        Company newCompany = new Company(2, "leocompany20", 0, new ArrayList<>());
        int existingId = 2;

        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .put("/companies/" + existingId);

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, this.oldController.getCompanies().get(1).getId().intValue());
        Assert.assertEquals("leocompany20", this.oldController.getCompanies().get(1).getName());
    }

    @Test
    public void should_delete_company_successfully_when_given_existing_id() {
        int existingId = 2;
        MockMvcResponse mvcResponse = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/" + existingId);

        Assert.assertEquals(HttpStatus.OK, mvcResponse.getStatusCode());
        Assert.assertEquals(2, this.oldController.getCompanies().size());
    }
}