package com.example.svacancy.repos;

import com.example.svacancy.Model.ChatRoom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatRoomRepo extends CrudRepository<ChatRoom, Long> {

    ChatRoom findByChatId(String chatId);
    List<ChatRoom> findAll();
//    ChatRoom findBySender(String sender);

    @Modifying
    @Query(value = "INSERT INTO chatroom_chatmessage (chatroom_id, chatmessages_id) VALUES (:chatRoomId, :chatMessageId)", nativeQuery = true)
    void insertChatMessageInChatRoom(@Param("chatRoomId") Long chatRoomId, @Param("chatMessageId") Long chatMessageId);

//    @Modifying
//    @Query(value = "INSERT INTO chatroom (chatid) VALUES (:chatId)", nativeQuery = true)
//    void insertChatMessageInChatRoom(@Param("chatId") int chatId);
}
