package com.example.svacancy.controller;

import com.example.svacancy.Model.ChatMessage;
import com.example.svacancy.Model.ChatRoom;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.repos.ChatRoomRepo;
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
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final ChatRoomRepo chatRoomRepo;
    private final UserService userService;

    @GetMapping("/")
    public String greeting(Map<String, Object> model, CsrfToken csrfToken) {
        model.put("_csrf", csrfToken);
        return "greeting";
    }

    @GetMapping("/login")
    public String loginError(@RequestParam(required = false) boolean error,
                             Model model
    ) {
        if (error) model.addAttribute("errorMessage", "Incorrect login or password");

        return "login";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       CsrfToken csrfToken,
                       @AuthenticationPrincipal User currentUser
    ) {
        model.addAttribute("_csrf", csrfToken);

        Page<VacancyDto> page = vacancyService.messageList(filter, pageable, currentUser);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PreAuthorize("hasAuthority('HR')")
    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User currentUser,
                      @Valid Vacancy vacancy,
                      BindingResult bindingResult,
                      Model model,
                      @PageableDefault Pageable pageable,
                      @RequestParam("file") MultipartFile file,
                      String salaryFrom, String salaryTo
    ) throws IOException {

//        vacancy.setAuthor(currentUser);
//        vacancy.setSalaryFromTo(salaryFrom, salaryTo);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("vacancy", vacancy);
        } else {
            vacancyService.saveVacancy(currentUser, salaryFrom, salaryTo, vacancy, file);
//            vacancyService.saveFile(vacancy, file);
            model.addAttribute("vacancy", null);
//            vacancyService.save(vacancy);
        }

        Page<VacancyDto> page = vacancyService.findAll(pageable, currentUser);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");

        return "main";
    }

//    @GetMapping("/user-messages/{author}")
//    public String userMessages(
//            @AuthenticationPrincipal User currentUser,
//            @PathVariable(name = "author") User author,
//            Model model,
//            @RequestParam(required = false) Vacancy vacancy,
//            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
//    ) {
//        Page<VacancyDto> page = vacancyService.vacancyListForUser(pageable, currentUser, author);
//        model.addAttribute("chatRooms", chatRoomRepo.);
//        //        model.addAttribute("userChannel", author);
////        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
////        model.addAttribute("subscribersCount", author.getSubscribers().size());
////        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
////        model.addAttribute("page", page);
////        model.addAttribute("vacancy", vacancy);
////        model.addAttribute("isCurrentUser", author.equals(currentUser));
////        model.addAttribute("url", "/user-messages/" + author.getId());
//
//        return "userMessages";
//    }

//    @GetMapping("/userprofile/{author}")
//    public String userMessages(
//            @AuthenticationPrincipal User currentUser,
//            @PathVariable(name = "author") User author,
//            Model model,
//            @RequestParam(required = false) Vacancy vacancy,
//            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
//    ){
//        Page<VacancyDto> page = vacancyService.vacancyListForUser(pageable, currentUser, author);
//
//        model.addAttribute("userChannel", author);
//        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
//        model.addAttribute("subscribersCount", author.getSubscribers().size());
//        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
//        model.addAttribute("page", page);
//        model.addAttribute("vacancy", vacancy);
//        model.addAttribute("isCurrentUser", author.equals(currentUser));
//        model.addAttribute("url", "/user-messages/" + author.getId());
//
//        return "userprofile";
//    }

    @PostMapping("/userprofile/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "user") Long user,
            @RequestParam("vacancy") Vacancy vacancy,
            @RequestParam("title") String title,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (vacancy.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(title)) {
                vacancy.setTitle(title);
            }

            if (!StringUtils.isEmpty(tag)) {
                vacancy.setTag(tag);
            }

            vacancyService.saveFile(vacancy, file);

            vacancyService.save(vacancy);
        }

        return "redirect:/userprofile/" + user;
    }

    @GetMapping("/messages/{vacancy}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Vacancy vacancy,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = vacancy.getLikes();

        vacancy.setLike(currentUser);
        vacancyService.save(vacancy);

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }

    @PostMapping("/respond")
    public String respondVacancy(
            Model model,
            @RequestParam(required = false) String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User currentUser,
            String currentVacancyId,
            @Valid ChatRoom chatRoom,
            @Valid ChatMessage chatMessage,
            BindingResult bindingResult
    ){
        Page<VacancyDto> page = vacancyService.messageList(filter, pageable, currentUser);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        vacancyService.respondVacancy(currentUser, currentVacancyId, chatRoom, chatMessage);

        return "main";
    }

}