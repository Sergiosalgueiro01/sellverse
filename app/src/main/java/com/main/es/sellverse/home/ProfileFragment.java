package com.main.es.sellverse.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.es.sellverse.R;
import com.main.es.sellverse.login.LoginActivity;


public class ProfileFragment extends Fragment {

    private View view;

    private TextView txtUsername, txtName, txtPhoneNumber, txtEmail;

    private ProgressBar progressBar;

    private String fullName, email, phone_number, username;

    private ImageView imageView;

    private FirebaseAuth auth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view=view;
        super.onViewCreated(view, savedInstanceState);
        setUpConfigButton();

        setUpProfile();
    }

    private void setUpProfile() {
        txtUsername = view.findViewById(R.id.txtUsernameProfile);
        txtEmail = view.findViewById(R.id.txtEmailProfile);
        txtName = view.findViewById(R.id.txtShowFullName);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneProfile);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user == null){
            Toast.makeText(getContext(), "Something is wrong!. User details are not available.", Toast.LENGTH_SHORT).show();
        }
        else{

            showUserProfile(user);
        }
    }

    private void showUserProfile(FirebaseUser user) {
        String userId = user.getUid();  //ID del usuario
        FirebaseFirestore f = FirebaseFirestore.getInstance();
        Task<DocumentSnapshot> collection = f.collection("user").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.getData().get("name").toString();
                                String fullName = name + " " + document.getData().get("surname").toString();
                                String phone_number = document.getData().get("phone_number").toString();
                                String email = document.getData().get("email").toString();
                                txtName.setText(fullName);
                                txtPhoneNumber.setText(phone_number);
                                txtEmail.setText(email);
                            } else {
                                Log.d("get failed with","No such document");
                            }
                        } else {
                            Log.d("get failed with ", task.getException().toString());
                        }
                    }
                });

        Task<DocumentSnapshot> collectionUser = f.collection("usernames").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String username = document.getData().get("username").toString();
                                txtUsername.setText(username);
                            } else {
                                Log.d("get failed with","No such document");
                            }
                        } else {
                            Log.d("get failed with ", task.getException().toString());
                        }
                    }
                });
    }

    private void setUpConfigButton() {
        ActionMenuItemView b = view.findViewById(R.id.configButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(b);
            }
        });
    }
    private void showPopup(ActionMenuItemView b){
        PopupMenu popup = new PopupMenu(view.getContext(),b);
        popup.inflate(R.menu.popup_config);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logOut:
                        logOut();

                }
                return false;
            }
        });
        popup.show();
    }

    private void logOut() {
        SharedPreferences preferences = view.getContext().
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        FirebaseAuth f = FirebaseAuth.getInstance();
        f.signOut();
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivity(intent);


    }
}