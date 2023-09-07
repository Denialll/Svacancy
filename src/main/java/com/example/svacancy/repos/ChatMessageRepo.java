package com.example.svacancy.repos;

import com.example.svacancy.Model.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepo extends CrudRepository<ChatMessage, Long> {
}
