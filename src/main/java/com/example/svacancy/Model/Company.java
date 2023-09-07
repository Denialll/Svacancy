package com.example.svacancy.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.*;

@Table(name = "Company")
@Entity
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

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getEmployes() {
        return employes;
    }

    public void setEmployes(Set<User> employes) {
        this.employes = employes;
    }

    public Set<Vacancy> getCompanyVacancies() {
        return companyVacancies;
    }

    public void setCompanyVacancies(Set<Vacancy> companyVacancies) {
        this.companyVacancies = companyVacancies;
    }

    @OneToMany(mappedBy = "company", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<User> employes = new HashSet<>();
    @OneToMany(mappedBy = "company", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<Vacancy> companyVacancies;

    public void addWorker(User user){
        employes.add(user);
    }
    public void removeEmploy(User user){this.employes.remove(user);}

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
