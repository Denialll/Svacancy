package com.example.svacancy.Model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CaptchaResponseDto (boolean success, @JsonAlias("error-codes") ArrayList<String> errorCodes){

}
