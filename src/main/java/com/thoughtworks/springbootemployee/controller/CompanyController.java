package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    public CompanyController() {
        this.companies.add(new Company("company1",100));
        this.companies.add(new Company("company2",100));
        this.companies.add(new Company("company3",100));
        this.companies.add(new Company("company4",100));
    }
    @GetMapping
    public List<Company> getCompanies(){
        return this.companies;
    }
}
