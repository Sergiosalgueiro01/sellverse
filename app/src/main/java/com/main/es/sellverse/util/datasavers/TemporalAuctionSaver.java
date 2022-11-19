package com.main.es.sellverse.util.datasavers;


import com.main.es.sellverse.model.Auction;

import java.util.ArrayList;
import java.util.List;

public class TemporalAuctionSaver {
    private static TemporalAuctionSaver instance;
    public List<Auction> auctions = new ArrayList<>();
    public Auction auction;

    public static TemporalAuctionSaver getInstance() {
        if (instance == null) {
            instance = new TemporalAuctionSaver();
        }
        return instance;
    }
}
