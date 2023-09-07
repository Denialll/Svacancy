package com.example.svacancy.services;

import com.example.svacancy.Model.*;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.exception.RegistrationException.VacancyException.RespondVacancyException;
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

    public void respondVacancy(User user, String currentVacancyId, ChatRoom chatRoom, ChatMessage chatMessage){
        Vacancy vacancy = vacancyRepo.findById(currentVacancyId);

//        if(user.getRespondedVacancies().contains(vacancy)) throw new RespondVacancyException("You have already respond on this vacancy");

        chatRoom.addChatMessage(chatMessage);
        user.addChatRoom(chatRoom);
//        user.addRespondedVacancy(vacancy);
        vacancy.addNumberOfResponded(user);

        vacancyRepo.save(vacancy);
//        userService.saveUser(user);
    }

    public void saveVacancy(User currentUser, String salaryFrom, String salaryTo, Vacancy vacancy, MultipartFile file) throws IOException {
        Company company = currentUser.getCompany();

        saveFile(vacancy, file);
        vacancy.setAuthor(currentUser);
        vacancy.setSalaryFromTo(salaryFrom, salaryTo);
        vacancy.setCompany(company);

        vacancyRepo.save(vacancy);
    }
}
