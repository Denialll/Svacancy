package com.example.svacancy.repos;

import com.example.svacancy.Model.ChatRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatRoomRepo extends CrudRepository<ChatRoom, Long> {

    ChatRoom findByChatId(String chatId);
    List<ChatRoom> findAll();

}
