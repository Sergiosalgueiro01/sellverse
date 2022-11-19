package com.main.es.sellverse.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class ChatMessage {

    private String id;
    private String text;
    private String userName;
    private Date time;

    public ChatMessage(String id, String text, String userName, Date time) {
        this.id = id;
        this.text = text;
        this.userName = userName;
        this.time = time;
    }

    public ChatMessage(){}

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage message = (ChatMessage) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                ", time=" + time +
                '}';
    }
}
