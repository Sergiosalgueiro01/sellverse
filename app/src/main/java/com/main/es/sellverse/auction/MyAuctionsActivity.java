package com.main.es.sellverse.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.listview.MyAuctionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAuctionsActivity extends AppCompatActivity {

    ListView listView;
    private List<Auction> listMyAuctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_auctions);
        setUpMyAuctions();
        setUpInflaterList();



    }

    private void setUpInflaterList(){
        listView = findViewById(R.id.listViewMyAuctions);
        System.out.println(listView);

        MyAuctionsAdapter adapter = new MyAuctionsAdapter(this, listMyAuctions);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TemporalAuctionSaver.getInstance().auction=listMyAuctions.get(position);
                Intent intent=new Intent(getApplicationContext(), AuctionInfoActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setUpMyAuctions() {
        listMyAuctions = new ArrayList<>();
        String idUser = FirebaseAuth.getInstance().getUid();
        for(Auction auction: TemporalAuctionSaver.getInstance().auctions){
            if(auction.getUserId().equals(idUser)){
                listMyAuctions.add(auction);
            }
        }
    }

}
