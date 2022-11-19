package com.main.es.sellverse.auction;

import androidx.appcompat.app.AppCompatActivity;
import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;
import com.main.es.sellverse.util.slider.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

public class AuctionInfoActivity extends AppCompatActivity {
    private SliderView sliderView;
    private Auction auction= TemporalAuctionSaver.getInstance().auction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_info);
        setUpSlider();
        setUpTitle();
        setUpPrice();
        setUpDescription();
        setUpNumberOfBilds();
        setUpCounter();
        setUpStartDate();
    }

    @SuppressLint("SetTextI18n")
    private void setUpStartDate() {
        TextView tv = findViewById(R.id.tvStartDateAction);
        tv.setText(tv.getText()+" "+ DateConvertionUtil.convert(auction.getStartTime()));
    }

    private void setUpCounter() {
        CountdownView cv = findViewById(R.id.count);
        Date now = new Date();
        long currentDate=now.getTime();
        long endDate=auction.getEndTime().getTime();
        long countDown = endDate-currentDate;
        cv.start(countDown);
    }

    private void setUpDescription() {
        TextView tv=findViewById(R.id.descriptionAuction);
        tv.setText(auction.getDescription());
    }

    @SuppressLint("SetTextI18n")
    private void setUpNumberOfBilds() {
        TextView tv=findViewById(R.id.numberOfBids);
        tv.setText(getString(R.string.number_of_bids)+" "+auction.getBids().size());
    }

    @SuppressLint("SetTextI18n")
    private void setUpPrice() {
        TextView tv= findViewById(R.id.tvCurrentPrice);
        tv.setText(getString(R.string.current_price)+" "+auction.getCurrentPrice()+"€");
    }

    private void setUpTitle() {
        TextView tv= findViewById(R.id.tvTitleAuction);
        tv.setText(auction.getTitle());
    }

    private void setUpSlider() {
        sliderView=findViewById(R.id.sliderView);

        SliderAdapter sliderAdapter = new SliderAdapter(auction.getImagesUrls().toArray(new String[0]));
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }
}