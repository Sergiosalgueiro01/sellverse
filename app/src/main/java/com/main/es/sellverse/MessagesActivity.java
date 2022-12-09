package com.main.es.sellverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;
import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.ChatAdapter;
import com.main.es.sellverse.model.ChatMessage;
import com.main.es.sellverse.model.User;
import com.main.es.sellverse.persistence.ChatDataBase;
import com.main.es.sellverse.persistence.UserDataBase;
import com.main.es.sellverse.util.datasavers.TemporalChatsSaver;

import java.util.Date;
import java.util.UUID;

public class MessagesActivity extends AppCompatActivity {

    private Chat chat;
    private String userLoggedId = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        this.chat = TemporalChatsSaver.getInstance().chat;
        String idUser = FirebaseAuth.getInstance().getUid();
        if(userLoggedId.equals(chat.getBuyerId()))
            UserDataBase.getUserByIdForMessages(chat.getSellerId(), this);
        else{
            UserDataBase.getUserByIdForMessages(chat.getBuyerId(), this);
        }
        Button send = findViewById(R.id.btnEnviar);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tx = (EditText)(findViewById(R.id.txtMensaje));
                String text = tx.getText().toString();
                ChatMessage message = new ChatMessage();
                message.setTime(new Date());

                message.setId(UUID.randomUUID().toString());

                message.setText(text);
                SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                message.setUserName(preferences.getString("username",""));
                chat.setLastMessage(text);
                chat.getMessages().add(message);
                ChatDataBase.createChat(chat);
                updateChat();
                tx.setText("");

            }
        });

    }


    public void setUpChat(User user){
        TextView txNombre = findViewById(R.id.nombre);

        txNombre.setText(user.getName());
        updateChat();

    }
    private void updateChat(){
        MessagesAdapter m = new MessagesAdapter(chat.getMessages());
        RecyclerView r = findViewById(R.id.rvMensajes);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        r.setLayoutManager(llm);
        r.setAdapter(m);
    }
}