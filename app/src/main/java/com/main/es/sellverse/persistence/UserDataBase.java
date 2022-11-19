package com.main.es.sellverse.persistence;



import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.login.UserNameActivity;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.util.datasavers.TemporalBooleanChecker;
import com.main.es.sellverse.util.datasavers.TemporalStringSaver;
import com.main.es.sellverse.util.hash.HashGenerator;
import com.main.es.sellverse.model.ChatMessage;
import com.main.es.sellverse.model.User;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.datasavers.TemporalUserSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.List;


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
        dbFirestore.collection("users").document(user.getId()).set(user.toMap());
    }

    public static void getUserById(String id){
        TemporalUserSaver.getInstance().user = null;
        Task<QuerySnapshot> collection = dbFirestore.collection("users").whereEqualTo("id", id)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                User user;
                for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                    user = new User();
                    user.setId(id);
                    user.setName(query.get("name").toString());
                    user.setSurname(query.get("surname").toString());
                    user.setEmail(query.get("email").toString());
                    user.setPhone_number(query.get("phone_number").toString());
                    TemporalUserSaver.getInstance().user = user;
                }
            }

        });
        while(!collection.isComplete()){

        }
    }
}
