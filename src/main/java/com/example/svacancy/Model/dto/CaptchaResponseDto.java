package com.example.svacancy.Model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CaptchaResponseDto (boolean success, @JsonAlias("error-codes") ArrayList<String> errorCodes){

}
