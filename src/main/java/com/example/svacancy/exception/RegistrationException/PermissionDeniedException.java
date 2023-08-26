package com.example.svacancy.exception.RegistrationException;


public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String message){
        super(message);
    }
}
