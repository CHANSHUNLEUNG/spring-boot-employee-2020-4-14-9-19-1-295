package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompanies(@RequestBody Company company) {
        this.companies.add(company);
        return company;
    }

    @PutMapping(path = "/{companyId}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        Company targetCompany = this.companies.stream()
                .filter(company -> company.getId() == companyId)
                .findFirst()
                .orElse(null);
        if (targetCompany == null) {
            return new ResponseEntity<>("Error, company does not exist", HttpStatus.BAD_REQUEST);
        }
        companies.set(companies.indexOf(targetCompany), newCompany);
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{companyID}")
    public ResponseEntity<Object> deleteCompanies(@PathVariable int companyID) {
        Company targetCompany = this.companies.stream()
                .filter(company -> company.getId() == companyID)
                .findFirst()
                .orElse(null);
        if (targetCompany == null) {
            return new ResponseEntity<>("Error, company does not exist", HttpStatus.BAD_REQUEST);
        }
        companies.remove(targetCompany);
        return new ResponseEntity<>("Remove company with id " + companyID + " successfully", HttpStatus.OK);
    }

}
