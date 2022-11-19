package com.main.es.sellverse.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.ChatAdapter;
import com.main.es.sellverse.model.GridAdapter;
import com.main.es.sellverse.persistence.ChatDataBase;
import com.main.es.sellverse.persistence.UserDataBase;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.datasavers.TemporalChatsSaver;

import java.util.List;


public class ChatFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //utiliza aqui el view hijo puta, no existe en fragmentos el findviewById pero puedes hacer el view.findViewbyId
    }

    public void setUpChats(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ChatDataBase.getChatsByUser(userID);
        List<Chat> chats =  TemporalChatsSaver.getInstance().chats;

        ChatAdapter chatAdapter = new ChatAdapter(requireActivity(), chats);
        RecyclerView r =  requireActivity().findViewById(R.id.rvChats);
        r.setAdapter(chatAdapter);


    }
}