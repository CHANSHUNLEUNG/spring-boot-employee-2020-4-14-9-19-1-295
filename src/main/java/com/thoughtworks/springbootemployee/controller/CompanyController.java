package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping(path = "/{companyID}")
    public Company getCompany(@PathVariable int companyID) {
        return companyService.getCompanyById(companyID);
    }

    @GetMapping(path = "/{companyID}/employees")
    public List<Employee> getEmployeesInCompany(@PathVariable int companyID) {
        return companyService.getEmployeesInCompany(companyID);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getCompanies
            (@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        return new ResponseEntity<>(companyService.getCompaniesWithPagination(page, pageSize), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompanies(@RequestBody Company company) {
        companyService.createCompanies(company);
        return company;
    }

    @PutMapping(path = "/{companyId}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        companyService.updateCompanies(companyId, newCompany);
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{companyId}")
    public ResponseEntity<Object> deleteCompanies(@PathVariable int companyId) {
        return new ResponseEntity<>("Remove company with id " + companyId + " successfully", HttpStatus.OK);
    }

}
