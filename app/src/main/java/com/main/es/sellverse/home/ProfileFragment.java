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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.login.LoginActivity;


public class ProfileFragment extends Fragment {

    private  View view;
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