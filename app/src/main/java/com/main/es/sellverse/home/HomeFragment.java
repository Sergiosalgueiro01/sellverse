package com.main.es.sellverse.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.main.es.sellverse.R;
import com.main.es.sellverse.databinding.FragmentHomeBinding;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.GridAdapter;
import com.main.es.sellverse.search.SearchActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private View view;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        return inflater.inflate(R.layout.fragment_home, binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        EditText searchText = view.findViewById(R.id.editSearch);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSearchActivity();
            }
        });
        setUpAuctionCatalog();
        //utiliza aqui el view hijo puta, no existe en fragmentos el findviewById pero puedes hacer el view.findViewbyId
    }

    private void setUpAuctionCatalog() {

        new RetrieveAuctionsTask().execute(
                requireActivity()
        );

    }
/*
    private void setUpAuctionCatalog() {
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(Task<GetTokenResult> task) {
                        String token = task.getResult().getToken();
                        Log.i("token",token);
                        AuctionInterface auctionInterface = getAuctionInterface();
                        Call<List<Auction>> call = auctionInterface.getActiveAuctions("Bearer "+token);
                        call.enqueue(new Callback<List<Auction>>() {
                            @Override
                            public void onResponse(Call<List<Auction>> call, Response<List<Auction>> response) {

                                List<Auction> res = response.body();
                                setUpGrid(res);
                            }
                            @Override
                            public void onFailure(Call<List<Auction>> call, Throwable t) {
                                System.out.println(t.getMessage()); //TODO make failure management
                            }
                        });
                    }
                });
    } */
    /*
    private AuctionInterface getAuctionInterface(){
        String ipSalgue="http://192.168.1.13:8080/";
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(ipSalgue)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        AuctionInterface auctionInterface = retrofit.create(AuctionInterface.class);
        return auctionInterface;
    } */

    private void setUpSearchActivity() {
        Intent intent = new Intent(requireActivity(), SearchActivity.class);
        startActivity(intent);
    }

}