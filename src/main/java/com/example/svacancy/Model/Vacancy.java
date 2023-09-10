package com.example.svacancy.Model;

import com.example.svacancy.Model.util.MessageHelper;
import com.example.svacancy.exception.RegistrationException.VacancyException.SalaryException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please enter a title")
    @Length(max = 2048, message = "Tag too long (more than 2kB)")
    private String title;
    @NotBlank(message = "Please enter a description")
    @Length(max = 10240, message = "Description too long (more than 10kB)")
    private String description;
    private String salary;
    @NotBlank(message = "Please enter a tag")
    @Length(max = 50, message = "Message too long (more than 2kB)")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<User> numberOfResponded = new HashSet<>();

    public Set<User> getNumberOfResponded() {
        return numberOfResponded;
    }

    public void addNumberOfResponded(User user) {
        this.numberOfResponded.add(user);
    }

    public void setNumberOfResponded(Set<User> numberOfResponded) {
        this.numberOfResponded = numberOfResponded;
    }

    public String getSalary() {
        return salary;
    }

    private void setSalary(String salary) {
        this.salary = salary;
    }

    public void setSalaryFromTo(String salaryFrom, String salaryTo) {
        if (salaryFrom.equals("")) salaryFrom = null;
        if (salaryTo.equals("")) salaryTo = null;

        if (salaryFrom != null && salaryTo != null) {
            if (Integer.parseInt(salaryFrom) >= Integer.parseInt(salaryTo)) {
                throw new SalaryException("Salary is incorrect");
            }
            this.salary = "From " + salaryFrom + " to " + salaryTo + " $";
        }
        if (salaryFrom == null && salaryTo == null) this.salary = "Not specified.";
        if (salaryFrom != null && salaryTo == null) this.salary = "From " + salaryFrom + " $";
        if (salaryFrom == null && salaryTo != null) this.salary = "To " + salaryTo + " $";
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    public Vacancy() {
    }

    public Vacancy(String title, String tag, User author) {
        this.author = author;
        this.title = title;
        this.tag = tag;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(this.author);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setLike(User user) {
        if (likes.contains(user)) likes.remove(user);
        else likes.add(user);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
