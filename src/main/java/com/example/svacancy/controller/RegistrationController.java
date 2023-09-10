package com.example.svacancy.controller;

import com.example.svacancy.Model.User;
import com.example.svacancy.Model.dto.CaptchaResponseDto;
import com.example.svacancy.exception.RegistrationException.EmailException;
import com.example.svacancy.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final String secret = "6Ldfdv4mAAAAAB5fzxR3zun3aY78XA88FIsJxRGt";

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.success()) model.addAttribute("captchaError", "Fill captcha");


        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) model.addAttribute("password2Error", "Password confirmation cannot be empty");


        if (!user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Password are different");
            return "registration";
        }

        if (bindingResult.hasErrors() || isConfirmEmpty || !response.success()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);

            model.mergeAttributes(errorsMap);

            return "registration";
        }

        userService.createNewUser(user);

        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendEmail(
            String email,
            Model model
    ) {
        if ("".equals(email)) throw new EmailException("Email can't be empty.");

        userService.passwordRecovery(email);

        model.addAttribute("MessageForUser", true);

        return "forgot-password";
    }

    @GetMapping("/change-password/{code}")
    public String passwordChange(
            @PathVariable String code,
            Model model
    ) {
        model.addAttribute("code", code);
        return "change-password";
    }

    @PostMapping("/change-password/{code}")
    public String passwordChange(
            @PathVariable String code,
            @RequestParam("password") String password,
            @RequestParam("password2") String passwordConfirm,
            Model model
    ) {
        if (!password.equals(passwordConfirm) || "".equals(password)) {
            model.addAttribute("passwordError", "Password are different");
            return "change-password/{code}";
        }

        userService.updatePassword(code, password);

        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Your password was successfully changed");

        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        userService.activateUser(code);

        model.addAttribute("messageType", "success");
        model.addAttribute("message", "User successfully activated!");

        return "login";
    }

}

