package com.main.es.sellverse.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.main.es.sellverse.R;
import com.main.es.sellverse.databinding.FragmentHomeBinding;
import com.main.es.sellverse.dto.MessageDto;
import com.main.es.sellverse.interfaces.AuctionInterface;
import com.main.es.sellverse.interfaces.HelloInterface;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.GridAdapter;
import com.main.es.sellverse.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
        setUpAuctionCatalog();
        //utiliza aqui el view hijo puta, no existe en fragmentos el findviewById pero puedes hacer el view.findViewbyId
    }

    private void setUpGrid(List<Auction> auctions){

        String[] auctionData = {"Rose","Lotus","Lily","Jasmine",
                "Tulip","Orchid","Levender","RoseMarry","Sunflower","Carnation", "Sunflower","Carnation", "Sunflower","Carnation"};
        int[] auctionImages = {R.drawable.btn_home,R.drawable.btn_home, R.drawable.btn_add,
                R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home,
                R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home, R.drawable.btn_home,
                R.drawable.btn_home, R.drawable.btn_home,R.drawable.btn_home};

        GridAdapter gridAdapter = new GridAdapter(getActivity(),auctionData,auctionImages);
        binding.gridViewCatalog.setAdapter(gridAdapter);

        binding.gridViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(),"You Clicked on "+ auctionData[position],Toast.LENGTH_SHORT).show();

            }
        });

        binding.gridViewCatalog.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY){
                if(oldScrollY > 0) {
                    binding.layoutSearch.removeAllViews();
                    System.out.println("baja");
                }else if(oldScrollY < 0) {
                    binding.layoutSearch.setVisibility(View.VISIBLE);
                    System.out.println("sube");
                }
            }
        });
    }

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
    }
    private AuctionInterface getAuctionInterface(){
        String ipSalgue="http://192.168.1.13:8080/";
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(ipSalgue)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        AuctionInterface auctionInterface = retrofit.create(AuctionInterface.class);
        return auctionInterface;
    }



}