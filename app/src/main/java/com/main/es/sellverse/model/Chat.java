package com.main.es.sellverse.model;

import java.util.List;
import java.util.Objects;

public class Chat {

    private String id;
    private String sellerId;
    private String buyerId;
    private String lastMessage;
    private List<ChatMessage> messages;

    public Chat(String id, String sellerId, String buyerId,String last, List<ChatMessage> messages) {
        this.id = id;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.lastMessage = last;
        this.messages = messages;
    }

    public Chat(){
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
