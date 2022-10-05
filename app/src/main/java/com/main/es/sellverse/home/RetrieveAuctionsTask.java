package com.main.es.sellverse.home;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.main.es.sellverse.R;
import com.main.es.sellverse.databinding.FragmentHomeBinding;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.GridAdapter;

import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.main.es.sellverse.R;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RetrieveAuctionsTask extends AsyncTask<Activity, Void, List<Auction>> {

    private Activity activity;

    @Override
    protected List<Auction> doInBackground(Activity... activities) {
        StringBuilder resultado = new StringBuilder();
        String data = null;
        try {
            URL url = new URL("https://vr52xjxzo0.execute-api.eu-central-1.amazonaws.com/dev/auctions/active");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String linea;

            while ((linea = rd.readLine()) != null) {
                resultado.append(linea);
            }
            rd.close();
            data = resultado.toString();
            // converti a list auctions y pasarlo aqui
            setUpGrid(null, activities[0]);
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private void setUpGrid(List<Auction> auctions, Activity activity){

        String[] auctionData = {"Rose","Lotus","Lily","Jasmine",
                "Tulip","Orchid","Levender","RoseMarry","Sunflower","Carnation", "Sunflower","Carnation", "Sunflower","Carnation"};
        int[] auctionImages = {R.drawable.btn_home,R.drawable.btn_home, R.drawable.btn_add,
                R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home,
                R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home, R.drawable.btn_home,
                R.drawable.btn_home, R.drawable.btn_home,R.drawable.btn_home};

        GridAdapter gridAdapter = new GridAdapter(activity,auctionImages, auctions);
        GridView g =  activity.findViewById(R.id.gridViewCatalog);
        g.setAdapter(gridAdapter);

        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(activity,"You Clicked on "+ auctions.get(position).getTitle(),Toast.LENGTH_SHORT).show();


            }
        });
    }

}
