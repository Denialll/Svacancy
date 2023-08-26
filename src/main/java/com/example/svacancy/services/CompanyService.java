package com.example.svacancy.services;


import com.example.svacancy.Model.Company;
import com.example.svacancy.repos.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;

    public void saveCompany(Company company){
        companyRepo.save(company);
    }

}
