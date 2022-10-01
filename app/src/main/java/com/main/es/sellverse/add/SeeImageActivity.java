package com.main.es.sellverse.add;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.main.es.sellverse.R;
import com.main.es.sellverse.util.datasavers.TemporalBitmapSaver;


public class SeeImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_image);
        ImageButton ib = findViewById(R.id.ibBack);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView iv = findViewById(R.id.ivPhoto);
       iv.setImageBitmap(TemporalBitmapSaver.getInstance().temporalBitmap);
    }


}