package com.main.es.sellverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.User;
import com.main.es.sellverse.persistence.UserDataBase;
import com.main.es.sellverse.util.datasavers.TemporalChatsSaver;

public class MessagesActivity extends AppCompatActivity {

    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        this.chat = TemporalChatsSaver.getInstance().chat;
        UserDataBase.getUserByIdForMessages(chat.getBuyerId(), this);
    }

    public void setUpChat(User user){
        TextView txNombre = findViewById(R.id.nombre);

        txNombre.setText(user.getName());


    }
}