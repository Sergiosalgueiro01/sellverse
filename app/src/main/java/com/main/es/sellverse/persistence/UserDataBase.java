package com.main.es.sellverse.persistence;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.login.UserNameActivity;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.util.datasavers.TemporalBooleanChecker;
import com.main.es.sellverse.util.datasavers.TemporalStringSaver;
import com.main.es.sellverse.util.hash.HashGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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


}
