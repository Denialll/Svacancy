package com.example.svacancy.Model.dto;

import com.example.svacancy.Model.ChatRoom;
import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.enums.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
        private Long id;
        private String username;
        private String password;
        private boolean active;
        private String email;
        private Set<Vacancy> vacancies;
        private String activationCode;
        private Company company;
        private Set<com.example.svacancy.Model.User> subscribers = new HashSet<>();
        private List<ChatRoom> chatRooms;
        private Set<com.example.svacancy.Model.User> subscriptions = new HashSet<>();
        private Set<Role> roles;
}
