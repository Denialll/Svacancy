package com.example.svacancy.controller;

import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.CoveringLetter;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.Model.enums.Role;
import com.example.svacancy.exception.RegistrationException.EmailException;
import com.example.svacancy.exception.RegistrationException.PermissionDeniedException;
import com.example.svacancy.services.CompanyService;
import com.example.svacancy.services.CoveringLetterService;
import com.example.svacancy.services.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CoveringLetterService coveringLetterService;
    private final VacancyService vacancyService;
    private final CompanyService companyService;
//    private final ControllerUtils controllerUtils;

    @PreAuthorize("hasAuthority('ADMIN')")//
    @GetMapping
    public String userList(Model model) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("letters", coveringLetterService.getAllLetters());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")//
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")//
    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {

        userService.saveUser(user, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile/{author}")
    public String getProfile(
            Model model,
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "author") User author,
            @RequestParam(required = false) Vacancy vacancy,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<VacancyDto> page = vacancyService.vacancyListForUser(pageable, currentUser, author);

        model.addAttribute("userChannel", author);
        model.addAttribute("email", author.getEmail());
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("isCurrentUser", author.equals(currentUser));
        model.addAttribute("url", "/user-messages/" + author.getId());

        return "profile";
    }

    @PostMapping("/sendcoveringletter")
    public String sendCoveringLetter(
            @AuthenticationPrincipal User currentUser,
            @Valid CoveringLetter coveringLetter,
            Company company,
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        System.out.println(company);
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

        userService.sendHRLetter(currentUser, company, coveringLetter);

        model.addAttribute("messageType", "success");
        model.addAttribute("messageLetter", "Letter was successfully sending");

        return "profile";
    }

//    @GetMapping("/profile/{author}")
//    public String userMessages(
//            @AuthenticationPrincipal User currentUser,
//            @PathVariable(name = "author") User author,
//            Model model
////            @RequestParam(required = false) Vacancy vacancy,
////            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
//    ) {
////        Page<MessageDto> page = vacancyService.messageListForUser(pageable, currentUser, author);
//
//        model.addAttribute("userChannel", author);
//        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
//        model.addAttribute("subscribersCount", author.getSubscribers().size());
//        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
////        model.addAttribute("page", page);
////        model.addAttribute("vacancy", vacancy);
//        model.addAttribute("isCurrentUser", author.equals(currentUser));
//        model.addAttribute("url", "/user/profile/" + author.getId());
//
//        return "user/profile";
//    }

    @PostMapping("profile/{author}")
    public String updateProfile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "author") User author,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) throws EmailException {
        if(!author.equals(currentUser)) throw new PermissionDeniedException("Not enough rights!");

        model.addAttribute("isCurrentUser", author.equals(currentUser));
//        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("username", author.getUsername());
        model.addAttribute("email", author.getEmail());
        model.addAttribute("url", "/user/profile/" + author.getId());

        userService.updateProfile(author, password, email);

        model.addAttribute("messageType", "success");
        if (currentUser.getActivationCode() != null) {
            model.addAttribute("message", "Password successfully changed! \nTo confirm the email change check them.");
        } else {
            model.addAttribute("message", "Password successfully changed!");
        }

        return "profile";
    }


    @GetMapping("userUpdate/{email}/{code}")
    public String userUpdate(
            @PathVariable String code,
            @PathVariable String email,
            Model model
    ) {
        userService.updateEmail(code, email);

        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Email successfully changed!");

        return "/profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.subscribe(currentUser, user);

        return "redirect:/user/profile/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unSubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.unsubscribe(currentUser, user);

        return "redirect:/user/profile/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(Model model,
                           @PathVariable User user,
                           @PathVariable String type
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }



}
