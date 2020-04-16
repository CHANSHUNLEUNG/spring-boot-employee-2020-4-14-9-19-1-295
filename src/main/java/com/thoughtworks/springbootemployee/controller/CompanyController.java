package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    public CompanyService companyService;

    public void setCompanies(List<Company> companies) {
        companyService.setCompanies(companies);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanies() {
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
        return new ResponseEntity<>(companyService.getCompaniesWithPagination(page, pageSize),HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompanies(@RequestBody Company company) {
        companyService.addCompanies(company);
        return company;
    }

    @PutMapping(path = "/{companyId}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        Boolean isUpdate = companyService.updateCompanies(companyId,newCompany);
        if(!isUpdate){
            return new ResponseEntity<>("Error, company is not exist.", HttpStatus.OK);
        }
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{companyId}")
    public ResponseEntity<Object> deleteCompanies(@PathVariable int companyId) {
        Boolean isDelete = companyService.deleteCompanies(companyId);
        if(!isDelete){
            return new ResponseEntity<>("Error, company is not exist", HttpStatus.OK);
        }
        return new ResponseEntity<>("Remove company with id " + companyId + " successfully", HttpStatus.OK);
    }

}
