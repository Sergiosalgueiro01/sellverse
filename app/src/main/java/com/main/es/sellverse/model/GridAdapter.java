package com.main.es.sellverse.model;

import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.main.es.sellverse.R;

import java.time.LocalDateTime;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    Context context;
    int[] image;
    String[] titles;
    List<Auction> auctions;

    LayoutInflater inflater;

    public GridAdapter(Context context, int[] image, List<Auction> auctions) {

        this.context = context;
        this.image = image;
        this.auctions = auctions;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_grid, null);

        }



        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView textName = convertView.findViewById(R.id.item_name);
        imageView.setImageResource(image[position]);

        TextView textCurrentPrice = convertView.findViewById(R.id.txPrice);
        TextView textRestTime = convertView.findViewById(R.id.txRestTime);

        imageView.setImageResource(image[position]);
        textName.setText(auctions.get(position).getTitle());
        textCurrentPrice.setText(auctions.get(position).getInitialPrice().toString());

        LocalDateTime t = auctions.get(position).getEndTime();
        String s = t.getYear() +"/"+t.getMonthValue()+"/"+t.getDayOfMonth();
        textRestTime.setText(s);

        return convertView;
    }


}