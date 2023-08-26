package com.example.svacancy.Model.util;

import com.example.svacancy.Model.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author){
        return author != null? author.getUsername() : "<none>";
    }
}
