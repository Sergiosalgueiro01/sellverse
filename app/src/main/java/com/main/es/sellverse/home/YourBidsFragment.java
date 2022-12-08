package com.main.es.sellverse.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.auction.AuctionInfoActivity;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Bid;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.listview.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class YourBidsFragment extends Fragment {

    private View view;
    private List<Auction>listOfBilledAuctions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_bids, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view=view;
        setUpList();
        setUpInflaterList();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpInflaterList() {
        ListView list = view.findViewById(R.id.lvBids);
        String idUser=FirebaseAuth.getInstance().getUid();
        list.setAdapter(new ListViewAdapter(getActivity(),listOfBilledAuctions,idUser));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TemporalAuctionSaver.getInstance().auction=listOfBilledAuctions.get(i);
                Intent intent=new Intent(getContext(), AuctionInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpList() {
        listOfBilledAuctions= new ArrayList<>();
        String idUser=FirebaseAuth.getInstance().getUid();
        for(Auction auction:TemporalAuctionSaver.getInstance().auctions){
            for (Bid bid:auction.getBids()){
                if(bid.getUserId().equals(idUser)){
                    listOfBilledAuctions.add(auction);
                    break;
                }

            }
        }
    }
}