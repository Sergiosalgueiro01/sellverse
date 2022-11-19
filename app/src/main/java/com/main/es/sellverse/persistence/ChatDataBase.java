package com.main.es.sellverse.persistence;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.home.ChatFragment;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.ChatMessage;
import com.main.es.sellverse.model.User;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.datasavers.TemporalChatsSaver;
import com.main.es.sellverse.util.datasavers.TemporalUserSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatDataBase {

    private static FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    private static FirebaseStorage dbStorage = FirebaseStorage.getInstance();

    public static void getChatsByUser(String userId, ChatFragment fragment){

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
                    List<String> messagesIds = (List<String>) query.get("messages");

                    getMessagesByIds(messagesIds, chat, fragment);
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
                            List<String> messagesIds = (List<String>) query.get("messages");

                            getMessagesByIds(messagesIds, chat, fragment );

                        }

                    }

                });
        while(!collection.isComplete() && !collection2.isComplete()){

        }
    }

    private static List<ChatMessage> getMessagesByIds(List<String> chatids, Chat chat, ChatFragment fragment){
        List<ChatMessage> res = new ArrayList<>();
        TemporalChatsSaver.getInstance().chats.clear();
        for(String id : chatids) {
            dbFirestore.collection("messages").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ChatMessage message;
                    message = new ChatMessage();
                    message.setId(documentSnapshot.get("id") +"");
                    message.setText(documentSnapshot.get("text") +"");
                    message.setUserName(documentSnapshot.get("username") + "");
                    message.setTime(DateConvertionUtil.unconvert(documentSnapshot.get("time")+""));
                    System.out.println(message);
                    res.add(message);
                    TemporalChatsSaver.getInstance().chats.add(chat);
                    chat.setMessages(res);
                    if(chatids.size()-1 == chatids.indexOf(id))
                        fragment.method();
                }
            });
        }
        return res;
    }

}
