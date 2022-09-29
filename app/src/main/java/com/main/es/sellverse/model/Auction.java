package com.main.es.sellverse.model;

import com.google.firebase.Timestamp;

import java.util.List;


public class Auction {

    private String id;
    private String title;
    private String description;
    private double initialPrice;
    private double currentPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private List<String> imagesUrls;
    private List<String> bids;

    public Auction() {
    }

    public Auction(String id, String title, String description, double initialPrice,
                   double currentPrice, Timestamp startTime,
                   Timestamp endTime, List<String> imagesUrls, List<String> bids) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imagesUrls = imagesUrls;
        this.bids = bids;
    }
}

