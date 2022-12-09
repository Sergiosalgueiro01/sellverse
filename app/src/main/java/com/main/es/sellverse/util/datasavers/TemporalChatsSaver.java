package com.main.es.sellverse.util.datasavers;

import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class TemporalChatsSaver {

    private static TemporalChatsSaver instance;
    public List<Chat> chats = new ArrayList<>();
    public Chat chat;

    public static TemporalChatsSaver getInstance() {
        if (instance == null) {
            instance = new TemporalChatsSaver();
        }
        return instance;
    }
}
