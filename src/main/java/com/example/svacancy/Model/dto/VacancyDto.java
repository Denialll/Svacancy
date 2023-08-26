package com.example.svacancy.Model.dto;

import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.util.MessageHelper;

public class VacancyDto {
    private Long id;
    private String title;
    private String description;
    private String salary;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private Boolean meLiked;

    public VacancyDto(Vacancy vacancy, Long likes, Boolean meLiked) {
        this.id = vacancy.getId();
        this.title = vacancy.getTitle();
        this.description = vacancy.getDescription();
        this.salary = vacancy.getSalary();
        this.tag = vacancy.getTag();
        this.author = vacancy.getAuthor();
        this.filename = vacancy.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getTag() {
        return tag;
    }
    public String getDescription(){ return description;}
    public User getAuthor() {
        return author;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    public String getAuthorName(){
        return MessageHelper.getAuthorName(this.author);
    }


}
