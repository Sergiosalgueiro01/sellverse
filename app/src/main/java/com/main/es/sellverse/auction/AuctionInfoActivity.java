package com.main.es.sellverse.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.Bid;
import com.main.es.sellverse.model.Chat;
import com.main.es.sellverse.persistence.AuctionDataBase;
import com.main.es.sellverse.persistence.ChatDataBase;
import com.main.es.sellverse.persistence.UserDataBase;
import com.main.es.sellverse.util.datasavers.TemporalAuctionSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;
import com.main.es.sellverse.util.slider.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import cn.iwgang.countdownview.CountdownView;

public class AuctionInfoActivity extends AppCompatActivity {
    private SliderView sliderView;
    private Auction auction= TemporalAuctionSaver.getInstance().auction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_info);
        setUpMenu();
        setUpSlider();
        setUpTitle();
        setUpPrice();
        setUpDescription();
        setUpNumberOfBilds();
        setUpCounter();
        setUpStartDate();
        setUpUsername();
        setUpAmountToBid();
        setUpBtnMakeTheBid();
        setUpChatButton();

    }

    private void setUpChatButton() {
        Button b = findViewById(R.id.startAChat);
        String idCurrentUser=FirebaseAuth.getInstance().getUid();
        String auctionUser=auction.getUserId();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new Chat();
                chat.setId(UUID.randomUUID().toString());
                chat.setBuyerId(idCurrentUser);
                chat.setSellerId(auctionUser);
                chat.setMessages(new ArrayList<>());
                ChatDataBase.createChat(chat);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    private void setUpMenu() {
        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.make_a_bild_add);
        toolbar.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.toolbar_info)));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setUpBtnMakeTheBid() {
        Button b = findViewById(R.id.btnMakeTheBid);
        TextView tv=findViewById(R.id.etNumber);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv.getText().toString().isEmpty()){
                    tv.requestFocus();
                    tv.setError(getString(R.string.price_cannot_be_empty));
                }
               else if(Double.parseDouble(tv.getText().toString())<= auction.getCurrentPrice()){
                    tv.requestFocus();
                    tv.setError(getString(R.string.price_is_lower_than_current_price));
                }
               else{
                   makeTheBid(Double.parseDouble(tv.getText().toString()));
                }

            }
        });
    }

    private void makeTheBid(double amount) {
        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Date date = new Date();
        date.setYear(date.getYear()+1900);
        auction.setCurrentPrice(amount);
        Bid bid=new Bid();
        bid.setAmount(amount);
        bid.setId(UUID.randomUUID().toString());
        bid.setDate(date);
        bid.setUserId(id);
        auction.getBids().add(bid);
        Date enDate=auction.getEndTime();
        enDate.setYear(enDate.getYear()+1900);
        auction.setEndTime(enDate);
        Date startDate=auction.getStartTime();
        startDate.setYear(startDate.getYear()+1900);
        auction.setStartTime(startDate);
        AuctionDataBase.createAction(auction);
        finish();
    }

    private void setUpAmountToBid() {
        EditText et = findViewById(R.id.etNumber);
        et.setHint("Min: "+auction.getCurrentPrice());

    }


    @SuppressLint("SetTextI18n")
    private void setUpStartDate() {
        TextView tv = findViewById(R.id.tvStartDateAction);
        Date date = auction.getStartTime();


        int month = date.getMonth()+1;
        int years =date.getYear()+1900;
        String s = years+"/"+month+"/"+date.getDate();
        tv.setText(tv.getText()+" "+s);


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
    private void setUpUsername() {
        String id = auction.getUserId();
        TextView tv=findViewById(R.id.tvSell);
        UserDataBase.addUsernameToTextView(id,tv);
    }

}