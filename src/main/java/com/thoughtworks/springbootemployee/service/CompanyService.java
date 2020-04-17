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
    private CompanyRepository companyRepository;


    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(int companyID) {
        return companyRepository.findById(companyID).orElse(null);
    }

    public List<Employee> getEmployeesInCompany(int companyID) {
        Company targetCompany = companyRepository.findById(companyID).orElse(null);
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

    public boolean updateCompanies(int companyId, Company newCompany) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);
        if (targetCompany == null) {
            return false;
        }
        companyRepository.save(newCompany);
        return true;
    }

    public boolean deleteCompanies(int companyId) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);
        if (targetCompany == null) {
            return false;
        }
        companyRepository.delete(targetCompany);
        return true;
    }

    public void addCompanies(Company company) {
        companyRepository.save(company);
    }
}
