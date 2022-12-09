package com.main.es.sellverse.persistence;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import com.main.es.sellverse.MessagesActivity;

import com.main.es.sellverse.MainActivity;
import com.main.es.sellverse.R;

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

    public static boolean checkIfUserNameExists(String username) {
        Task<QuerySnapshot> collection = dbFirestore.collection("usernames").whereEqualTo("username", username).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (queryDocumentSnapshots.getDocuments().size() != 0)
                            TemporalBooleanChecker.getInstance().checker = true;
                    }
                });
        while (!collection.isComplete()) {

        }
        if (TemporalBooleanChecker.getInstance().checker) {
            TemporalBooleanChecker.getInstance().checker = false;
            return true;
        }
        return false;

    }

    public static void addUsername(String username, String id) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        dbFirestore.collection("usernames").document(id).set(
                result
        );
    }

    public static void checkIfUserHasUsername(String id, Activity activity) {

        dbFirestore.collection("usernames").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Intent intent;
                if (documentSnapshot.get("username") != null) {

                    intent = new Intent(activity, HomeActivity.class);

                } else {
                    intent = new Intent(activity, UserNameActivity.class);
                }
                activity.startActivity(intent);
                activity.finish();

            }
        });


    }
    public static void checkIfUserHasUsernameMain(String id, MainActivity activity) {
        SharedPreferences preferences =
                activity.getSharedPreferences(activity.getString(R.string.prefs_file),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (id == null)
            id = "no$3";
        String finalId = id;
        dbFirestore.collection("usernames").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!finalId.equals("no$3")) {
                    Intent intent;
                    if (documentSnapshot.get("username") != null) {
                        editor.putString("username", documentSnapshot.get("username").toString());
                        editor.commit();
                        intent = new Intent(activity, HomeActivity.class);

                    } else {
                        intent = new Intent(activity, UserNameActivity.class);
                    }
                    activity.startActivity(intent);
                    activity.finish();

                } else {
                    activity.setContentView(R.layout.activity_main);
                    activity.setTheme(R.style.Theme_Sellverse_NoActionBar);
                    activity.setUpButton();
                }
            }
        });
    }

    public static void addUsernameToTextView(String id, TextView tv) {

        dbFirestore.collection("usernames").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.get("username") != null) {

                    tv.setText(tv.getText() + " " + documentSnapshot.get("username"));

                }


            }
        });


    }
    public static void createUser(User user){
        dbFirestore.collection("user").document(user.getId()).set(user.toMap());
    }

    public static void getUserById(String id, ChatAdapter chatAdapter, ChatAdapter.ViewHolder viewHolder, Chat chat, final ChatAdapter.OnItemClickListener listener){
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
                loadUsername(user,chatAdapter,viewHolder,chat,listener);

            }
        });
    }

    private static void loadUsername(User user,ChatAdapter chatAdapter,
                                     ChatAdapter.ViewHolder viewHolder, Chat chat, final ChatAdapter.OnItemClickListener listener) {
        dbFirestore.collection("usernames").document(user.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.get("username") != null) {

                  user.setUsername(documentSnapshot.get("username").toString());
                  TemporalUserSaver.getInstance().user = user;
                  chatAdapter.method(viewHolder, chat, listener);
                }


            }
        });
    }

    public static void getUserByIdForMessages(String id, MessagesActivity activity){
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
                activity.setUpChat(user);
            }
        });
    }
}
