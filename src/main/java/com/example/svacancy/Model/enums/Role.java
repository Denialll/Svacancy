package com.example.svacancy.Model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
    USER,HR,COMPANYCREATOR,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
