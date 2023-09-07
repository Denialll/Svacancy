package com.example.svacancy.controller;

import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.repos.VacancyRepo;
import com.example.svacancy.services.CompanyService;
import com.example.svacancy.services.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final VacancyService vacancyService;
    private final VacancyRepo vacancyRepo;

    @GetMapping("company/{companyId}")
    public String getCompany(
            Model model,
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "companyId") Company company,
            @RequestParam(required = false) Vacancy vacancy,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        User creator = company.getCreator();
        Page<VacancyDto> page = vacancyRepo.findByCompany(pageable, creator.getCompany(), currentUser);

        model.addAttribute("page", page);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("url", "/user-messages/" + creator.getId());
        model.addAttribute("isCurrentUser", currentUser.equals(creator));
        model.addAttribute("author", creator);
        model.addAttribute("company", creator.getCompany());
        model.addAttribute("employees", creator.getCompany().getEmployees());
        model.addAttribute("vacancies", creator.getCompany().getCompanyVacancies());

        return "company";
    }

    @PostMapping("/createcompany")
    public String createCompany(
            @AuthenticationPrincipal User currentUser,
            @Valid Company company,
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<VacancyDto> page = vacancyService.vacancyListForUser(pageable, currentUser, currentUser);

        model.addAttribute("page", page);
        model.addAttribute("isCurrentUser", true);
        model.addAttribute("userChannel", currentUser);
        model.addAttribute("subscriptionsCount", currentUser.getSubscriptions().size());
        model.addAttribute("subscribersCount", currentUser.getSubscribers().size());
        model.addAttribute("isSubscriber", currentUser.getSubscribers().contains(currentUser));
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("url", "/user/profile/" + currentUser.getId());

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);

            return "profile";
        }

        companyService.sendAboutCompany(currentUser.getId(), company);

        model.addAttribute("messageType", "success");
        model.addAttribute("messageLetter", "Letter was successfully sending. Wait 4-5 days and we will activate your company!");

        return "profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/adminpanel/acceptCompany/{company}")
    public String acceptCompany(@PathVariable(name = "company") Company company) {
        companyService.acceptCompany(company);

        return "redirect:/adminpanel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/adminpanel/rejectCompany/{company}")
    public String rejectCompany(@PathVariable(name = "company") Company company) {
        companyService.deleteCompany(company);

        return "redirect:/adminpanel";
    }

//    @PreAuthorize("hasAuthority('COMPANYCREATOR')")
//    @PostMapping("/removeEmploy/{employId}")
//    public String removeEmploy(@PathVariable(name = "employId") User user) {
//        companyService.removeEmploy(user);
//
//        return "redirect:/adminpanel";
//    }


}
