package com.main.es.sellverse.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.main.es.sellverse.R;
import com.main.es.sellverse.databinding.FragmentHomeBinding;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.model.GridAdapter;
import com.main.es.sellverse.search.SearchActivity;

import java.util.List;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private View view;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        return inflater.inflate(R.layout.fragment_home, binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        EditText searchText = view.findViewById(R.id.editSearch);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSearchActivity();
            }
        });
        setUpAuctionCatalog();
        //utiliza aqui el view hijo puta, no existe en fragmentos el findviewById pero puedes hacer el view.findViewbyId
    }

    private void setUpAuctionCatalog() {

        new RetrieveAuctionsTask().execute(
                this
        );

    }

    public void setUpGrid(List<Auction> auctions){

        String[] auctionData = {"Rose","Lotus","Lily","Jasmine",
                "Tulip","Orchid","Levender","RoseMarry","Sunflower","Carnation", "Sunflower","Carnation", "Sunflower","Carnation"};
        int[] auctionImages = {R.drawable.btn_home,R.drawable.btn_home, R.drawable.btn_add,
                R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home,
                R.drawable.btn_home,R.drawable.btn_home,R.drawable.btn_home, R.drawable.btn_home,
                R.drawable.btn_home, R.drawable.btn_home,R.drawable.btn_home};

        GridAdapter gridAdapter = new GridAdapter(requireActivity(),auctionImages, auctions);
        GridView g =  requireActivity().findViewById(R.id.gridViewCatalog);
        g.setAdapter(gridAdapter);

        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(requireActivity(),"You Clicked on "+ auctions.get(position).getTitle(),Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void setUpSearchActivity() {
        Intent intent = new Intent(requireActivity(), SearchActivity.class);
        startActivity(intent);
    }

}