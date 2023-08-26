package com.example.svacancy.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Table(name = "Company")
@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date dateOfCreate = new Date();
    @OneToOne
    private User creator;
    private String INN;
    private Boolean active = false;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> workers;
    @OneToMany
    private Set<Vacancy> companyVacancies;

    public void addWorker(User user){
        workers.add(user);
    }
}
