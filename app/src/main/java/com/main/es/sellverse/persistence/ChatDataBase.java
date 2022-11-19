package com.main.es.sellverse.persistence;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.ChatMessage;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.datasavers.TemporalChatsSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatDataBase {

    private static FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    private static FirebaseStorage dbStorage = FirebaseStorage.getInstance();

    public static void getChatsByUser(String userId){

        TemporalChatsSaver.getInstance().chats.clear();
        Task<QuerySnapshot> collection = dbFirestore.collection("chats")
                .whereEqualTo("seller",userId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Chat chat;
                for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                    chat= new Chat();
                    chat.setId(query.get("id").toString());
                    chat.setBuyerId(query.get("buyer").toString());
                    chat.setSellerId(query.get("seller").toString());
                    chat.setLastMessage(query.get("lastmessage").toString());
                    HashMap<String,Object> list = (HashMap<String, Object>) query.get("messages");
                    List<String> messagesIds = new ArrayList<>();
                    list.forEach((s, o) -> messagesIds.add(o.toString()));

                    chat.setMessages(getMessagesByIds(messagesIds));

                    TemporalChatsSaver.getInstance().chats.add(chat);
                }
            }
        });
        Task<QuerySnapshot> collection2 = dbFirestore.collection("chats")
                .whereEqualTo("buyer",userId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Chat chat;
                        for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                            chat= new Chat();
                            chat.setId(query.get("id").toString());
                            chat.setBuyerId(query.get("buyer").toString());
                            chat.setSellerId(query.get("seller").toString());
                            chat.setLastMessage(query.get("lastmessage").toString());
                            HashMap<String,Object> list = (HashMap<String, Object>) query.get("messages");
                            List<String> messagesIds = new ArrayList<>();
                            list.forEach((s, o) -> messagesIds.add(o.toString()));

                            chat.setMessages(getMessagesByIds(messagesIds));

                            TemporalChatsSaver.getInstance().chats.add(chat);

                        }
                    }

                });
        while(!collection.isComplete() && !collection2.isComplete()){}
    }

    private static List<ChatMessage> getMessagesByIds(List<String> chatids){
        List<ChatMessage> res = new ArrayList<>();
        for(String id : chatids) {
            Task<QuerySnapshot> collection = dbFirestore.collection("chats")
                    .whereEqualTo("id", id)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ChatMessage message;
                            for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                                message = new ChatMessage();
                                message.setId(id);
                                message.setText(query.get("text").toString());
                                message.setUserName(query.get("username").toString());
                                message.setTime(DateConvertionUtil.unconvert(query.getString("time")));

                                res.add(message);
                            }
                        }
                    });
        }
        return res;
    }


}
