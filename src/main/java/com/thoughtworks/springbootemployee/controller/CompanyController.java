package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    public CompanyController() {
        this.companies.add(new Company(1, "company1", 100));
        this.companies.add(new Company(2, "company2", 100));
        this.companies.add(new Company(3, "company3", 100));
        this.companies.add(new Company(4, "company4", 100));
        this.companies.get(0).getEmployees().add(new Employee(1, "leo1", 18, "male", 80000));
        this.companies.get(0).getEmployees().add(new Employee(2, "leo2", 18, "male", 80000));
        this.companies.get(0).getEmployees().add(new Employee(3, "leo3", 18, "male", 80000));
    }

    @GetMapping
    public List<Company> getCompanies() {
        return this.companies;
    }

    @GetMapping(path = "/{companyID}")
    public Company getCompany(@PathVariable int companyID) {
        Company returnCompany = this.companies.stream()
                .filter(company -> company.getId() == companyID)
                .findFirst()
                .orElse(null);
        return returnCompany;
    }

    @GetMapping(path = "/{companyID}/employees")
    public List<Employee> getEmployeesInCompany(@PathVariable int companyID) {
        Company returnCompany = this.companies.stream()
                .filter(company -> company.getId() == companyID)
                .findFirst()
                .orElse(null);
        return returnCompany.getEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getCompanies
            (@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize;
        if (this.companies.size() < startIndex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } else if (this.companies.size() > startIndex && this.companies.size() < endIndex) {
            return new ResponseEntity<>(this.companies.subList(startIndex, companies.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.companies.subList((page - 1) * pageSize, page * pageSize), HttpStatus.OK);
    }
    @PostMapping
    public Company addCompanies(@RequestBody Company company){
        this.companies.add(company);
        return company;
    }
    @PutMapping
    public ResponseEntity<Object> updateCompanies(@RequestBody Company newCompany) {
        Company targetCompany = this.companies.stream()
                .filter(company -> company.getId() == newCompany.getId())
                .findFirst()
                .orElse(null);
        if (targetCompany == null) {
            return new ResponseEntity<>("Error, company does not exist", HttpStatus.BAD_REQUEST);
        }
        companies.set(companies.indexOf(targetCompany), newCompany);
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }

}
