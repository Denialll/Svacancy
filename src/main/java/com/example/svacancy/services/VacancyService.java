package com.example.svacancy.services;

import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.repos.VacancyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepo vacancyRepo;
    @Value("${upload.path}")
    private String uploadPath;

    public void saveFile(Vacancy vacancy, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            vacancy.setFilename(resultFilename);
        }
    }
    public Page<VacancyDto> messageList(String filter, Pageable pageable, User user){
        if (filter != null && !filter.isEmpty()) {
            return vacancyRepo.findByTag(filter, pageable, user);
        } else {
            return vacancyRepo.findAll(pageable, user);
        }
    }

    public Page<VacancyDto> vacancyListForUser(Pageable pageable, User currentUser, User author) {
        return vacancyRepo.findByUser(pageable, author, currentUser);
    }

    public void save(Vacancy vacancy) {
        vacancyRepo.save(vacancy);
    }

    public Page<VacancyDto> findAll(Pageable pageable, User currentUser) {
        return vacancyRepo.findAll(pageable, currentUser);
    }

    public Vacancy findById(String id){
        return vacancyRepo.findById(id);
    }

    public void saveVacancy(User currentUser, String salaryFrom, String salaryTo, Vacancy vacancy, MultipartFile file) throws IOException {
        Company company = currentUser.getCompany();

        saveFile(vacancy, file);
        vacancy.setAuthor(currentUser);
        vacancy.setSalaryFromTo(salaryFrom, salaryTo);
        vacancy.setCompany(company);

        vacancyRepo.save(vacancy);
//        company.set
    }
}
