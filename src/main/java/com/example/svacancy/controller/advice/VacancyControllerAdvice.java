package com.example.svacancy.controller.advice;


import com.example.svacancy.Model.User;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.exception.RegistrationException.VacancyException.SalaryException;
import com.example.svacancy.services.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class VacancyControllerAdvice {

    private final VacancyService vacancyService;

    @ExceptionHandler(SalaryException.class)
    public String handleSalaryException(SalaryException e,
                                        Model model,
                                        @RequestParam(required = false) String filter,
                                        @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                        CsrfToken csrfToken,
                                        @AuthenticationPrincipal User currentUser) {
        e.printStackTrace();

        model.addAttribute("_csrf", csrfToken);

        Page<VacancyDto> page = vacancyService.messageList(filter, pageable, currentUser);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        model.addAttribute("salaryError", e.getMessage());

        return "main";
    }


}
