package com.main.es.sellverse.util.listview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Bid;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Activity context;
    private List<Auction>listOfBilledAuctions;
    private String idUser;

    public ListViewAdapter(Activity context, List<Auction>list,String idUser){
        super();
        this.context=context;
        this.listOfBilledAuctions=list;
        this.idUser=idUser;
    }

    @Override
    public int getCount() {
        return listOfBilledAuctions.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfBilledAuctions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=vi.inflate(R.layout.list_bids,viewGroup,false);
            ImageView iv=view.findViewById(R.id.imageBid);

            RelativeLayout rl=view.findViewById(R.id.rlColorBids);
            rl.setBackgroundResource(getBackGroundColor(i));
            Picasso.get()
                    .load(listOfBilledAuctions.get(i).getImagesUrls().get(0)).into(iv);
            TextView tv=view.findViewById(R.id.titleBid);
            tv.setText(listOfBilledAuctions.get(i).getTitle());
            TextView tvYouBidded= view.findViewById(R.id.youBidded);
            tvYouBidded.setText(tvYouBidded.getText().toString()+" "+getHowManyBidded(i)+"€");
            TextView tvBiggestBid=view.findViewById(R.id.biggestBid);
            tvBiggestBid.setText(tvBiggestBid.getText().toString()+" "
                    +listOfBilledAuctions.get(i).getCurrentPrice()+"€");
        return view;


    }

    private double getHowManyBidded(int pos) {
        List<Bid>listOfBids=listOfBilledAuctions.get(pos).getBids();
        for(int i = listOfBids.size()-1;i>=0;i--){
            if(listOfBids.get(i).getUserId().equals(idUser))
                return listOfBids.get(i).getAmount();
        }
        return 0;
    }

    private int getBackGroundColor(int i) {
        List<Bid>listOfBids=listOfBilledAuctions.get(i).getBids();
        if(listOfBids.get(listOfBids.size()-1).getUserId().equals(idUser))
            return R.color.green;
        return R.color.red;
    }
}
