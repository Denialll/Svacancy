package com.example.svacancy.repos;

import com.example.svacancy.Model.ChatRoom;
import com.example.svacancy.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatRoomRepo extends CrudRepository<ChatRoom, Long> {

    ChatRoom findByChatId(String chatId);
    List<ChatRoom> findAll();
//    @Query("SELECT * FROM ChatRoom where id = user.id")
//    List<ChatRoom> findByUser(@Param("user") User user);

}
