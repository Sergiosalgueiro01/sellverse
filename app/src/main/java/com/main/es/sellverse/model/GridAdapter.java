package com.main.es.sellverse.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.main.es.sellverse.R;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    Context context;
    List<Bitmap> images;
    List<Auction> auctions;
    String userId;
    LayoutInflater inflater;

    public GridAdapter(Context context, List<Auction> auctions,String userId) {
        this.userId=userId;
        this.context = context;
        this.auctions = getAuctionsThatAreNotInUser(auctions);
    }

    private List<Auction> getAuctionsThatAreNotInUser(List<Auction> auctions) {
        List<Auction>result= new ArrayList<>();
        Date date = new Date();

        for(Auction auction:auctions){
            if(!auction.getUserId().equals(userId) &&
                    auction.getStartTime().before(date)
                    && auction.getEndTime()
                    .after(date))
                result.add(auction);
        }
        return  result;
    }


    @Override
    public int getCount() {
        return auctions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_grid, null);

        }




        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView textName = convertView.findViewById(R.id.item_name);
        TextView textCurrentPrice = convertView.findViewById(R.id.txPrice);
        TextView textRestTime = convertView.findViewById(R.id.txRestTime);

        String url =auctions.get(position).getImagesUrls().get(0);
        Picasso.get()
                .load(url).into(imageView);

        textName.setText(auctions.get(position).getTitle());
        textCurrentPrice.setText(auctions.get(position).getCurrentPrice().toString());
        Date t = auctions.get(position).getEndTime();
        int month = t.getMonth()+1;
        int years =t.getYear()+1900;
        String s = years+"/"+month+"/"+t.getDate();
        textRestTime.setText(s);

        return convertView;
    }


}