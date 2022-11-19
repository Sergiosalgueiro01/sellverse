package com.main.es.sellverse.util.datasavers;

import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.User;

import java.util.ArrayList;
import java.util.List;

public class TemporalUserSaver {

    private static TemporalUserSaver instance;
    public List<User> users = new ArrayList<>();
    public User user;

    public static TemporalUserSaver getInstance() {
        if (instance == null) {
            instance = new TemporalUserSaver();
        }
        return instance;
    }
}
