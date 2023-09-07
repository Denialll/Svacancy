package com.example.svacancy.Model;

import com.example.svacancy.Model.enums.MessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MessageType messageType;
    @NotNull
    private String content;
    //    @ManyToOne
    //    @JoinColumn(name = "sender_id")
    @NotNull
    private String sender;
    //    @ManyToOne
    //    @JoinColumn(name = "recipient_id")
    private String recipient;
    private Date date;


    public MessageType getMessageType() {
        return messageType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getFormattedDate() {
        Date date = this.date;
        if (date == null) return "";
        SimpleDateFormat sdf;
        String formattedTime = "";

        Calendar calendarToday = Calendar.getInstance();
        Calendar calendarMessage = Calendar.getInstance();
        calendarMessage.setTime(date);

        int dayOfWeekToday = calendarToday.get(Calendar.DAY_OF_WEEK);
        int dayOfMonthToday = calendarToday.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int dayOfWeekMessage = calendarMessage.get(Calendar.DAY_OF_WEEK);
        int dayOfMonthMessage = calendarMessage.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        if (dayOfWeekMessage == dayOfWeekToday && dayOfMonthMessage == dayOfMonthToday) {
            sdf = new SimpleDateFormat("HH:mm");
            formattedTime = sdf.format(date);
            formattedTime += " | Today";
        } else if (dayOfWeekMessage + 1 == dayOfWeekToday && dayOfMonthMessage == dayOfMonthToday) {
            sdf = new SimpleDateFormat("HH:mm");
            formattedTime = sdf.format(date);
            formattedTime += " | Yesterday";
        } else {
            sdf = new SimpleDateFormat("HH:mm | MM MMMM");
            formattedTime = sdf.format(date);
        }

        return formattedTime;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", messageType=" + messageType +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", date=" + date.toString() +
                '}';
    }


    public MessageType getType() {
        return messageType;
    }

    public void setType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public Long getId() {
        return this.id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
