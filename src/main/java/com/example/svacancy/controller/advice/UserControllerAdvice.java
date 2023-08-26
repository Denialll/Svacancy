package com.example.svacancy.controller.advice;


import com.example.svacancy.exception.RegistrationException.ActivationCodeException;
import com.example.svacancy.exception.RegistrationException.EmailException;
import com.example.svacancy.exception.RegistrationException.UsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class UserControllerAdvice {

    @ExceptionHandler(EmailException.class)
    public String handleEmailException(EmailException e, Model model){
        e.printStackTrace();
        model.addAttribute("emailError", e.getMessage());
        String methodWithException = e.getStackTrace()[0].getMethodName();
        log.info(methodWithException);
        if(methodWithException.equals("updateProfile")) return "profile";
        if(methodWithException.equals("sendEmail") || methodWithException.equals("passwordRecovery")) return "forgot-password";
        return  "registration";
    }

    @ExceptionHandler(UsernameException.class)
    public String handleUsernameException(UsernameException e, Model model){
        e.printStackTrace();
        model.addAttribute("usernameError", e.getMessage());
        return  "registration";
    }

    @ExceptionHandler(ActivationCodeException.class)
    public String handleActivationCodeException(ActivationCodeException e, Model model){
        e.printStackTrace();
        model.addAttribute("messageType", "danger");
        model.addAttribute("message", e.getMessage());
        return  "registration";
    }

}
