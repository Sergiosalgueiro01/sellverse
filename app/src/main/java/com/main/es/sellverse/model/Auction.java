package com.main.es.sellverse.model;



import com.google.firebase.firestore.Exclude;
import com.main.es.sellverse.util.date.DateConvertionUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Auction {

    private String id;
    private String title;
    private String description;
    private Double initialPrice;
    private Double currentPrice;
    private Date startTime;
    private Date endTime;
    private List<String> imagesUrls;
    private List<String> bids = new ArrayList<>();
    private String userId;

    public Auction() {
    }

    public Auction(String id, String title, String description,
                   double initialPrice, double currentPrice, Date startTime,
                   Date endTime, List<String> imagesUrls, List<String> bids, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imagesUrls = imagesUrls;
        this.bids = bids;
        this.userId = userId;
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

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public List<String> getBids() {
        return bids;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public void setBids(List<String> bids) {
        this.bids = bids;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("title", title);
        result.put("description", description);
        result.put("initialPrice", initialPrice);
        result.put("startTime", DateConvertionUtil.convert(startTime));
        result.put("endTime",DateConvertionUtil.convert(endTime));
        result.put("imagesUrls",getUrls());
        result.put("currentPrice",getCurrentPrice());
        result.put("userId",getUserId());
        return result;
    }
    private  Map<String, Object> getUrls(){
        HashMap<String,Object>urls = new HashMap<>();
        int i=0;
        for (String url:getImagesUrls()) {
            urls.put(i+"",url);
            i++;
        }
        return urls;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return Objects.equals(id, auction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
                ", userId=" + userId +
                '}';
    }
}

