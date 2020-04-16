package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
