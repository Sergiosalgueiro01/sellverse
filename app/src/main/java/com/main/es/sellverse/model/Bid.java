package com.main.es.sellverse.model;

import com.google.firebase.firestore.Exclude;
import com.main.es.sellverse.util.date.DateConvertionUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bid {
    private double amount;
    private String id;
    private Date date;
    private String userId;
    public Bid(){}

    public double getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("amount",amount);
        result.put("id",id);
        result.put("date",DateConvertionUtil.convert(date));
        result.put("userId",userId);
        return result;
    }
}
