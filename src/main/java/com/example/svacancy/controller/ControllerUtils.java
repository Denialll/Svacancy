package com.example.svacancy.controller;

import com.example.svacancy.Model.Vacancy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {



    static Map<String, String> getErrorsMap(BindingResult bindingResult) {
//        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
//                fieldError -> fieldError.getField() + "Error",
//                FieldError::getDefaultMessage
//        );

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        System.out.println(fieldErrors);


        Map<String, String> collect1 = fieldErrors.stream().distinct().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        ));




//        Map<String, String> collect = bindingResult.getFieldErrors().stream().distinct().collect(collect1);

        return collect1;
    }

}
