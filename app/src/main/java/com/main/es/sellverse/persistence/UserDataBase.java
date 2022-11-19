package com.main.es.sellverse.persistence;



import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.login.UserNameActivity;
import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.model.ChatAdapter;
import com.main.es.sellverse.util.datasavers.TemporalBooleanChecker;
import com.main.es.sellverse.model.User;
import com.main.es.sellverse.util.datasavers.TemporalUserSaver;

import java.util.HashMap;


public class UserDataBase {
    private static FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    private static FirebaseStorage dbStorage = FirebaseStorage.getInstance();

    public static boolean checkIfUserNameExists(String username){
        Task<QuerySnapshot> collection= dbFirestore.collection("usernames").whereEqualTo("username",username).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(queryDocumentSnapshots.getDocuments().size()!=0)
                    TemporalBooleanChecker.getInstance().checker=true;
            }
        });
        while(!collection.isComplete()){

        }
        if(TemporalBooleanChecker.getInstance().checker){
            TemporalBooleanChecker.getInstance().checker=false;
            return true;}
        return false;

    }
    public static void addUsername(String username,String id){
        HashMap<String, Object> result = new HashMap<>();
        result.put("username",username);
        dbFirestore.collection("usernames").document(id).set(
                result
        );
    }
    public static void  checkIfUserHasUsername(String id, Activity activity){

         dbFirestore.collection("usernames").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Intent intent;
                if(documentSnapshot.get("username")!=null){
                    intent=new Intent(activity, HomeActivity.class);

                }
                else{
                    intent=new Intent(activity, UserNameActivity.class);
                }
                activity.startActivity(intent);
                activity.finish();

            }
        });





    }


    public static void createUser(User user){
        dbFirestore.collection("user").document(user.getId()).set(user.toMap());
    }

    public static void getUserById(String id, ChatAdapter chatAdapter, ChatAdapter.ViewHolder viewHolder, Chat chat){
        TemporalUserSaver.getInstance().user = null;

        dbFirestore.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User user;

                user = new User();
                user.setId(documentSnapshot.get("id") +"");
                user.setName(documentSnapshot.get("name")+"");
                user.setSurname(documentSnapshot.get("surname")+"");
                user.setEmail(documentSnapshot.get("email") +"");
                user.setPhone_number(documentSnapshot.get("phone_number") +"");
                TemporalUserSaver.getInstance().user = user;
                chatAdapter.method(viewHolder, chat);
            }
        });
    }
}
