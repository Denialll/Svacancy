package com.example.svacancy.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.*;

@Table(name = "Company")
@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Company name can't be empty")
    private String name;
    private Date dateOfCreate = new Date();
    @OneToOne
    private User creator;
    @NotBlank(message = "INN can't be empty")
    private String INN;
    @NotBlank(message = "Description can't be empty")
    private String description;
    private Boolean active = false;
    @OneToMany(mappedBy = "company", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<User> employees = new HashSet<>();
    @OneToMany(mappedBy = "company", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<Vacancy> companyVacancies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addWorker(User user) {
        employees.add(user);
    }

    public void removeEmploy(User user) {
        this.employees.remove(user);
    }

}
