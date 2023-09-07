package com.example.svacancy.services;


import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.enums.Role;
import com.example.svacancy.repos.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final UserService userService;
    private final CompanyRepo companyRepo;

    public void saveCompany(Company company) {
        companyRepo.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public void sendAboutCompany(Long userId, Company company) {
        Optional<User> userFromDb = userService.findById(userId);
        User user = userFromDb.get();

        if (user.getCompany() == null) user.setCompany(company);
        company.setCreator(user);
        company.addWorker(user);

        companyRepo.save(company);
    }

    public void acceptCompany(Company company) {
        User user = company.getCreator();
        company.setActive(true);

        companyRepo.save(company);

        user.getRoles().add(Role.COMPANYCREATOR);
        user.getRoles().add(Role.HR);

        userService.saveUser(user);
    }

    public void deleteCompany(Company company) {
        Set<User> userList = company.getEmployes();

        for (User user : userList) {
            user.setCompany(null);
            userService.saveUser(user);
        }

        companyRepo.delete(company);
    }

    public void removeEmploy(User user) {
        Company company = user.getCompany();
        company.removeEmploy(user);

        companyRepo.save(company);
    }
}
