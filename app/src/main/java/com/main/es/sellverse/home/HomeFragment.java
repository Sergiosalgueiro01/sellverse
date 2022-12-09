package com.main.es.sellverse.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.auction.AuctionInfoActivity;
import com.main.es.sellverse.databinding.FragmentHomeBinding;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.GridAdapter;
import com.main.es.sellverse.persistence.AuctionDataBase;
import com.main.es.sellverse.search.SearchActivity;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.tasks.RetrieveAuctionsTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
        setUpSwiperRefresh();



        //utiliza aqui el view hijo puta, no existe en fragmentos el findviewById pero puedes hacer el view.findViewbyId
    }

    private void setUpSwiperRefresh() {
        SwipeRefreshLayout s = view.findViewById(R.id.swipe);

        s.setEnabled(true);




        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUpAuctionCatalog();
                s.setRefreshing(false);
            }
        });
    }

    private void setUpAuctionCatalog() {
        new RetrieveAuctionsTask().execute(this);

    }

    public void setUpGrid(List<Auction> auctions){
        String idUser= FirebaseAuth.getInstance().getUid();
        List<Auction>auctionToGrid= getAuctionsThatAreNotInUser(auctions,idUser);
        GridAdapter gridAdapter = new GridAdapter(view.getContext(), auctionToGrid,idUser);
        GridView g =  getActivity().findViewById(R.id.gridViewCatalog);
        g.setAdapter(gridAdapter);
        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TemporalAuctionSaver.getInstance().auction=auctionToGrid.get(position);
                Intent intent=new Intent(getContext(),AuctionInfoActivity.class);
                startActivity(intent);

            }
        });
    }
    private List<Auction> getAuctionsThatAreNotInUser(List<Auction> auctions,String userId) {

        List<Auction>result= new ArrayList<>();
        Date date = new Date();
        date.setDate(date.getDate()+1);//porque el emulador tiene distinta hora

        for(Auction auction:auctions){

            if(!auction.getUserId().equals(userId) &&
                    auction.getStartTime().before(date)
                    && auction.getEndTime()
                    .after(date)) {

                result.add(auction);
            }
        }
        return  result;
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpAuctionCatalog();



    }

    private void setUpFilterActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

}