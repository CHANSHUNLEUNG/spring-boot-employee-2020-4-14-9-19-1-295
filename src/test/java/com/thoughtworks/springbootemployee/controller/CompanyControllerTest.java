package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

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
        int companyID = 2;

        companyService.getCompanyById(companyID);

        Mockito.verify(companyRepository, Mockito.times(1)).findById(2);
    }

    @Test
    public void should_return_all_employees_of_a_company_when_given_companyId() {
        int companyID = 2;

        Mockito.when(companyRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new Company()));
        companyService.getEmployeesInCompany(companyID);

        Mockito.verify(companyRepository, Mockito.times(1)).findById(companyID);
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
        Company oldCompany = new Company(2, "leocompany2", 0, new ArrayList<>());
        Company newCompany = new Company(2, "leocompany20", 0, new ArrayList<>());

        Mockito.when(companyRepository.findById(2)).thenReturn(Optional.of(oldCompany));

        companyService.updateCompanies(2, newCompany);

        Mockito.verify(companyRepository, Mockito.times(1)).save(Mockito.any(Company.class));
    }

    @Test
    public void should_delete_company_successfully_when_given_existing_id() {
        int companyId = 2;

        companyService.deleteCompanies(companyId);

        Mockito.verify(companyRepository, Mockito.times(1)).deleteById(companyId);
    }
}