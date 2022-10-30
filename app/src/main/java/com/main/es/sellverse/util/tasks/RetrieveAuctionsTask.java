package com.main.es.sellverse.util.tasks;

import android.os.AsyncTask;


import com.main.es.sellverse.home.HomeFragment;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.persistence.AuctionDataBase;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;



import java.util.List;


public class RetrieveAuctionsTask extends AsyncTask<HomeFragment, Void, List<Auction>> {


    private HomeFragment home;
    private List<Auction> auctions;

    @Override
    protected List<Auction> doInBackground(HomeFragment... homes) {
        AuctionDataBase.getAuctions();
        this.home=homes[0];
        return null;
    }

    @Override
    protected void onPostExecute(List<Auction> a){

        auctions = TemporalAuctionSaver.getInstance().auctions;
        System.out.println(a);
        home.setUpGrid(auctions);
    }







}
