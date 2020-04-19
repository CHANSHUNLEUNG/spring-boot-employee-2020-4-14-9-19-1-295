package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

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
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public boolean updateCompanies(int companyId, Company newCompany) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);
        if (targetCompany == null) {
            return false;
        }
        companyRepository.save(newCompany);
        return true;
    }

    public void deleteCompanies(int companyId) {
        companyRepository.deleteById(companyId);
    }

    public void createCompanies(Company company) {
        companyRepository.save(company);
    }
}
