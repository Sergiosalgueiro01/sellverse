package com.main.es.sellverse.model;



import java.util.List;


public class Auction {

    private String id;
    private String title;
    private String description;
    private double initialPrice;
    private double currentPrice;
   // private Timestamp startTime;
   // private Timestamp endTime;
    private List<String> imagesUrls;
    private List<String> bids;

    public Auction() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    //public Timestamp getStartTime() {
    //    return startTime;
    //}

    //public Timestamp getEndTime() {
    //    return endTime;
    //}

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public List<String> getBids() {
        return bids;
    }
/*
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
*/
    /*
    @Override
    public String toString() {
        return "Auction{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialPrice=" + initialPrice +
                ", currentPrice=" + currentPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", imagesUrls=" + imagesUrls +
                ", bids=" + bids +
                '}';
    }

     */
}

