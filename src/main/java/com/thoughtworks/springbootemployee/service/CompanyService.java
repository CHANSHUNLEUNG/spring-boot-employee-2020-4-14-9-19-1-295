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
        assert targetCompany != null;
        return targetCompany.getEmployees();
    }

    public List<Company> getCompaniesWithPagination(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public void updateCompanies(int companyId, Company newCompany) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);
        if (targetCompany == null) {
            return;
        }

        targetCompany.setName(newCompany.getName());
        targetCompany.setEmployees(newCompany.getEmployees());
        targetCompany.setEmployeesNumber(newCompany.getEmployeesNumber());

        companyRepository.save(newCompany);
    }

    public void deleteEmployeesInCompany(int companyId) {
        companyRepository.deleteEmployeesInCompany(companyId);
    }

    public void createCompanies(Company company) {
        companyRepository.save(company);
    }
}
