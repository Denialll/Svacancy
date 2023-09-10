package com.example.svacancy.Model;

import com.example.svacancy.Model.ChatMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return chatId.equals(chatRoom.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
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