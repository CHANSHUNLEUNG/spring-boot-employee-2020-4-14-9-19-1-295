package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from employee where company_id = :companyId", nativeQuery = true)
    void deleteEmployeesInCompany(@Param("companyId") int companyId);
}
