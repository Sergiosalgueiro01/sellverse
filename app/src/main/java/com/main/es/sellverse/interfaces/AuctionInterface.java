package com.main.es.sellverse.interfaces;

import com.main.es.sellverse.dto.MessageDto;
import com.main.es.sellverse.model.Auction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AuctionInterface {

    @GET("auctions/active")
    Call<List<Auction>> getActiveAuctions(@Header("Authorization") String token);

    @GET("auctions")
    Call<MessageDto> getAuctions(@Header("Authorization") String token);
}
