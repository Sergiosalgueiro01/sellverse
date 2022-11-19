package com.main.es.sellverse.persistence;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Bid;
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
                }

            }

        });


    }
    public static  void getAuctions() {

        Task<QuerySnapshot> collection = dbFirestore.collection("auctions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                TemporalAuctionSaver.getInstance().auctions.clear();
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
                    auction.setUserId(query.getString("userId"));
                    HashMap<String,Object>list = (HashMap<String, Object>) query.get("imagesUrls");
                    List<String>urls=new ArrayList<>();
                    list.forEach((s, o) ->
                          urls.add(o.toString()));
                    auction.setImagesUrls(urls);
                    HashMap<String,Object>list2=(HashMap<String, Object>) query.get("bids");
                    List<Bid>bids=getBids((HashMap<String, Object>) query.get("bids"));
                    auction.setBids(bids);

                    TemporalAuctionSaver.getInstance().auctions.add(auction);

                }
            }

        });
        while(!collection.isComplete()){

        }
    }

    private static List<Bid> getBids(HashMap<String, Object> bids) {
        List<Bid> list = new ArrayList<>();
        bids.forEach((s, o) ->{
            HashMap<String,Object>bidHashed= (HashMap<String, Object>) o;
            Bid bid=new Bid();
            bidHashed.forEach((s1, o1) ->{

                if(s1.equals("amount"))
                    bid.setAmount((Double) o1);
                else if(s1.equals("date"))
                    bid.setDate(DateConvertionUtil.unconvert((String) o1));
                else if(s1.equals("id"))
                    bid.setId((String) o1);
                else
                    bid.setUserId((String) o1);

            });
            list.add(bid);
        });
        return list;
    }


    }




