package com.main.es.sellverse.persistence;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.datasavers.TemporalStringSaver;
import com.main.es.sellverse.util.datasavers.TemporalUriSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class AuctionDataBase {
    private static FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    private static FirebaseStorage dbStorage = FirebaseStorage.getInstance();
    public static void createAction(Auction auction){
        dbFirestore.collection("auctions").document(auction.getId()).set(auction.toMap());

    }
    public static void addImage(Uri uri, int position, String idAuction){
        String s ="auction";
        List<String> urls = new ArrayList<>();

       dbStorage.getReference().child(s).child("hola"+"/img"+idAuction)
                .putFile(uri);




    }
    public static void getImage(Uri uri,String idAuction) {
        TemporalStringSaver.getInstance().list.clear();
        dbStorage.getReference().child("auction").child("hola" + "/img" + idAuction).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                if (uri != null) {

                    String s = uri.toString();
                    TemporalStringSaver.getInstance().list.add(s);
                    System.out.println(uri.toString());
                }

                // System.out.println(uri.toString());
            }

        });


    }
    public static  void getAuctions() {
        TemporalAuctionSaver.getInstance().auctions.clear();
        Task<QuerySnapshot> collection = dbFirestore.collection("auctions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Auction auction;
                for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                    auction= new Auction();
                    auction.setId(query.get("id").toString());
                    auction.setTitle(query.get("title").toString());
                    auction.setDescription(query.get("description").toString());
                    auction.setInitialPrice(Double.parseDouble(query.get("initialPrice").toString()));
                    auction.setCurrentPrice(Double.parseDouble(query.get("currentPrice").toString()));
                    auction.setStartTime(DateConvertionUtil.unconvert(query.getString("startTime")));
                    auction.setEndTime(DateConvertionUtil.unconvert(query.getString("endTime")));
                    HashMap<String,Object>list = (HashMap<String, Object>) query.get("imagesUrls");
                    List<String>urls=new ArrayList<>();
                  list.forEach((s, o) ->
                          urls.add(o.toString()));

                    auction.setImagesUrls(urls);
                    TemporalAuctionSaver.getInstance().auctions.add(auction);

                }
                System.out.println(TemporalAuctionSaver.getInstance().auctions);
            }

        });
        while(!collection.isComplete()){

        }
    }



    }

