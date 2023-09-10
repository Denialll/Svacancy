package com.example.svacancy.controller;

import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.dto.VacancyDto;
import com.example.svacancy.Model.enums.Role;
import com.example.svacancy.exception.RegistrationException.EmailException;
import com.example.svacancy.exception.RegistrationException.PermissionDeniedException;
import com.example.svacancy.services.CompanyService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final VacancyService vacancyService;
    private final CompanyService companyService;

    @PreAuthorize("hasAuthority('ADMIN')")//
    @GetMapping("/adminpanel")
    public String userList(Model model) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("companies", companyService.getAllCompanies());

        return "adminpanel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")//
    @GetMapping("adminpanel/{user}")
    public String userEditForm(@PathVariable User user, Model model) {

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")//
    @PostMapping("/adminpanel")
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {

        userService.saveUser(user, username, form);

        return "redirect:/adminpanel";
    }

    @GetMapping("userprofile/{author}")
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

//    @PostMapping("/user-messages/{user}")
//    public String updateMessage(
//            @AuthenticationPrincipal User currentUser,
//            @PathVariable Long user,
//            @RequestParam("id") Message message,
//            @RequestParam("text") String text,
//            @RequestParam("tag") String tag,
//            @RequestParam("file") MultipartFile file
//    ) throws IOException {
//        if (message.getAuthor().equals(currentUser)) {
//            if (!StringUtils.isEmpty(text)) {
//                message.setText(text);
//            }
//
//            if (!StringUtils.isEmpty(tag)) {
//                message.setTag(tag);
//            }
//
//            saveFile(message, file);
//
//            messageRepo.save(message);
//        }
//
//        return "redirect:/user-messages/" + user;
//    }

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

    @PostMapping("user/{author}")
    public String updateProfile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "author") User author,
            @RequestParam String password,
            @RequestParam String email,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws EmailException {
        Page<VacancyDto> page = vacancyService.vacancyListForUser(pageable, currentUser, currentUser);
        model.addAttribute("page", page);

        if(!author.equals(currentUser)) throw new PermissionDeniedException("Not enough rights!");

        model.addAttribute("isCurrentUser", author.equals(currentUser));
        model.addAttribute("userChannel", author);
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


    @GetMapping("user/userupdate/{email}/{code}")
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
