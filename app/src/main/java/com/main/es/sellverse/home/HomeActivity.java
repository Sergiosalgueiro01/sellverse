package com.main.es.sellverse.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;




import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.main.es.sellverse.R;
import com.main.es.sellverse.add.AddPublicationActivity;
import com.main.es.sellverse.interfaces.HelloInterface;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView ;
    private Fragment homeFragment = new HomeFragment();
    private Fragment profileFragment = new ProfileFragment();
    private Fragment chatFragment = new ChatFragment();
    private Fragment yourBidsFragment = new YourBidsFragment();

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //setUpLogin();
        setUpFragments();
    }
/**
    private void setUpLogin() {
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(Task<GetTokenResult> task) {
                        String token = task.getResult().getToken();
                        Log.i("token",token);
                        HelloInterface helloInterface = getHelloInterface();
                        Call<MessageDto> call = helloInterface.getHello("Bearer "+token);
                        call.enqueue(new Callback<MessageDto>() {
                            @Override
                            public void onResponse(Call<MessageDto> call, Response<MessageDto> response) {
                                if(!response.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                                    Log.e("err",response.message());
                                    return;
                                }
                                MessageDto dto = response.body();
                                System.out.println(dto.getMessage());

                            }

                            @Override
                            public void onFailure(Call<MessageDto> call, Throwable t) {

                            }
                        });
                    }
                });
    }*/
    private HelloInterface getHelloInterface(){
        String ipSalgue="http://192.168.1.13:8080/";
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(ipSalgue)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        HelloInterface helloInterface = retrofit.create(HelloInterface.class);
        return helloInterface;
    }

    /**
     * Set up the navigation menu
     */
    private void setUpFragments() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        View add = findViewById(R.id.add_icon);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.bids_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,yourBidsFragment).commit();
                        return true;


                    case R.id.profile_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;

                    case R.id.chat_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment).commit();
                        return true;
                }

                return false;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * Opens the add activity
     */
    private void openAddActivity() {
        Intent intent = new Intent(this, AddPublicationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.up_in,R.anim.up_out);

    }

}


