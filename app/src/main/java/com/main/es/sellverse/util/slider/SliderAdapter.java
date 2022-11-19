package com.main.es.sellverse.util.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.main.es.sellverse.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    private String[] urls;
    public SliderAdapter(String urls[]){
        this.urls=urls;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        Picasso.get().load(urls[position]).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        ImageView imageView;
        public Holder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.ivPhotoAuction);
        }


    }
}
