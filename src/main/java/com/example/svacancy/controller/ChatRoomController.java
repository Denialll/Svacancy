package com.example.svacancy.controller;


import com.example.svacancy.Model.ChatMessage;
import com.example.svacancy.Model.ChatRoom;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.enums.MessageType;
import com.example.svacancy.repos.ChatMessageRepo;
import com.example.svacancy.repos.ChatRoomRepo;
import com.example.svacancy.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static java.lang.String.format;

@Controller
public class ChatRoomController {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private ChatRoomRepo chatRoomRepo;
    @Autowired
    private ChatMessageRepo chatMessageRepo;
    @Autowired
    private UserRepo userRepo;

    @MessageMapping("/chat/{roomId}/sendMessage")
    @Transactional
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        System.out.println("ROOMID: " + roomId);
        chatMessageRepo.save(chatMessage);

        ChatRoom chatRoom = chatRoomRepo.findByChatId(roomId);
        if (chatRoom == null) {
            chatRoom = new ChatRoom();
            chatRoom.setChatId(roomId);
        }
        chatRoom.addChatMessage(chatMessage);
        chatRoomRepo.save(chatRoom);

        logger.info(roomId + " Chat message recieved is " + chatMessage.getContent());
        messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId,
                        @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor,
                        Model model) {

        User user = userRepo.findByUsername(chatMessage.getSender());
        ChatRoom chatRoom = chatRoomRepo.findByChatId(roomId);

        if(!user.getChatRooms().contains(chatRoom)) {
            user.addChatRoom(chatRoom);
            userRepo.save(user);
        }

        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            ChatMessage leaveMessage = new ChatMessage();
            leaveMessage.setType(MessageType.LEAVE);
            leaveMessage.setSender(chatMessage.getSender());
            messagingTemplate.convertAndSend(format("/chat-room/%s", currentRoomId), leaveMessage);
        }

        headerAccessor.getSessionAttributes().put("name", chatMessage.getSender());

        messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(
            @PathVariable("author") User currentUser,
            Model model
    ) {
        model.addAttribute("chatRooms", currentUser.getChatRooms());

        return "userMessages";
    }
}