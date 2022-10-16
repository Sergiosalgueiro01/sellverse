package com.main.es.sellverse.home;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.main.es.sellverse.model.Auction;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RetrieveAuctionsTask extends AsyncTask<HomeFragment, Void, List<Auction>> {


    private HomeFragment home;
    private List<Auction> auctions;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected List<Auction> doInBackground(HomeFragment... homes) {
        StringBuilder resultado = new StringBuilder();
        String data = null;
        this.home = homes[0];
        try {
            URL url = new URL("https://vr52xjxzo0.execute-api.eu-central-1.amazonaws.com/dev/auctions/active");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String linea;

            while ((linea = rd.readLine()) != null) {
                resultado.append(linea);
            }
            rd.close();
            data = resultado.toString();
            auctions = parseAuctions(data);

           return auctions;
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Auction> a){
        home.setUpGrid(auctions);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Auction> parseAuctions(String data){
        List<Auction> res = new ArrayList<>();
        JsonArray json = new Gson().fromJson(data, JsonArray.class);
        for(JsonElement auc : json){
            Auction a = new Auction();
            JsonObject jo = auc.getAsJsonObject();
            a.setId(jo.get("id").getAsString());
            a.setTitle(jo.get("title").getAsString());
            a.setDescription(jo.get("description").getAsString());
            a.setInitialPrice(jo.get("initialPrice").getAsDouble());
            a.setCurrentPrice(jo.get("currentPrice").getAsDouble());
            a.setUserId(jo.get("userId").getAsString());
            List<String> bids = new ArrayList<>();
            for(JsonElement bid: jo.get("bids").getAsJsonArray()){
                bids.add(bid.getAsString());
            }
            a.setBids(bids);
            List<String> imagePaths = new ArrayList<>();
            for(JsonElement img: jo.get("imagePaths").getAsJsonArray()){
                imagePaths.add(img.getAsString());
            }
            a.setImagesUrls(imagePaths);
            JsonObject joStartDate = jo.get("startTime").getAsJsonObject();
            a.setStartTime(parseDate(joStartDate));
            JsonObject joEndTime = jo.get("startTime").getAsJsonObject();
            a.setEndTime(parseDate(joEndTime));
            res.add(a);
        }

        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDateTime parseDate(JsonObject joDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = joDate.get("year").getAsString() + "-" +
                fixDate(joDate.get("monthValue").getAsString()) +"-"+
                fixDate(joDate.get("dayOfMonth").getAsString())+ " "+
                fixDate(joDate.get("hour").getAsString())+":"+
                fixDate(joDate.get("minute").getAsString())+":"+
                fixDate(joDate.get("second").getAsString());
        return LocalDateTime.parse(s, formatter);
    }

    private String fixDate(String data){
        if(data.length() < 2)
            return "0"+data;
        else
            return data;
    }

}
