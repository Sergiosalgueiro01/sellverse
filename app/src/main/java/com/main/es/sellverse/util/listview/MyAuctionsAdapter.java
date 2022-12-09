package com.main.es.sellverse.util.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Auction;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAuctionsAdapter extends BaseAdapter {

    Context context;

    List<Auction> myAuctions;

    public MyAuctionsAdapter(Context context, List<Auction> myAuctions) {
        this.context = context;
        this.myAuctions = myAuctions;
    }

    @Override
    public int getCount() {
        return myAuctions.size();
    }

    @Override
    public Object getItem(int position) {
        return myAuctions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView imAuctions;
        TextView nombre;
        TextView maxBids;

        Auction ac = myAuctions.get(position);

        if(view== null){
            view = LayoutInflater.from(context).inflate(R.layout.item_my_auctions, null);
        }
        imAuctions = view.findViewById(R.id.imgMyAuctions);
        nombre = view.findViewById(R.id.txtNombreProductoMisAuctions);
        maxBids = view.findViewById(R.id.txtCantidadPujadaMisAuctions);

        Picasso.get()
                .load(myAuctions.get(position).getImagesUrls().get(0)).into(imAuctions);

        nombre.setText(ac.getTitle());
        maxBids.setText("Puja Actual: " +ac.getCurrentPrice() + "€");

        return view;
    }
}
