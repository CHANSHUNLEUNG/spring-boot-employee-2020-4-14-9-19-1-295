package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    public CompanyController() {
        this.companies.add(new Company(1,"company1",100));
        this.companies.add(new Company(2,"company2",100));
        this.companies.add(new Company(3,"company3",100));
        this.companies.add(new Company(4,"company4",100));
    }
    @GetMapping
    public List<Company> getCompanies(){
        return this.companies;
    }
    @GetMapping(path = "/{companyID}")
    public Company getCompany(@PathVariable int companyID){
        Company returnCompany = this.companies.stream()
                .filter(company -> company.getId() == companyID)
                .findFirst()
                .orElse(null);
        return returnCompany;
    }
}
