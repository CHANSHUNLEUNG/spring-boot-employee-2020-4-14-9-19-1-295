package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    public CompanyRepository companyRepository;


    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public void setCompanies(List<Company> companies) {
        companyRepository.setCompanies(companies);
    }

    public Company getCompanyById(int companyID) {
        return companyRepository.findById(companyID);
    }

    public List<Employee> getEmployeesInCompany(int companyID) {
        Company targetCompany = companyRepository.findById(companyID);
        return targetCompany.getEmployees();
    }

    public List<Company> getCompaniesWithPagination(int page, int pageSize) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize;
        List<Company> companies = companyRepository.findAll();

        if (companies.size() < startIndex) {
            return new ArrayList<>();
        } else if (companies.size() > startIndex && companies.size() < endIndex) {
            return companies.subList(startIndex, companies.size());
        }
        return companies.subList((page - 1) * pageSize, page * pageSize);
    }
}
