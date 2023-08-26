package com.example.svacancy.controller;


import com.example.svacancy.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    @GetMapping("/{creator}")
    public String getCompany(
            Model model,
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "creator") User creator
    ) {
        model.addAttribute("isCurrentUser", currentUser.equals(creator));
        model.addAttribute("company", creator.getCompany());
        System.out.println(creator);
        System.out.println(creator.getCompany());
        model.addAttribute("isCurUsrFromThsComp", creator.getCompany().getWorkers().contains(currentUser));
        model.addAttribute("employees", creator.getCompany().getWorkers());

        return "company";
    }

}
