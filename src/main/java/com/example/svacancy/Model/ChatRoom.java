package com.example.svacancy.Model;

import com.example.svacancy.Model.ChatMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ChatRoom")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String chatId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public void addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

    @Override
    public String toString() {

        String str = "{ChatRoom{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", chatMessages=";
        for (ChatMessage chatMessage : chatMessages) {
            str += chatMessage.getContent() + ",";
        }

        return str;

    }
//    private String hrid;
//    private String userId;
}