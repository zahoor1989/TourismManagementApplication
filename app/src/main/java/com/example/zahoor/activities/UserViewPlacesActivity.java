package com.example.zahoor.activities;

import adapters.UserPlacesAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zahoor.model.TourPlace;
import com.example.zahoor.sql.TouristPlaceDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserViewPlacesActivity extends AppCompatActivity implements UserPlacesAdapter.ItemClicked {
    private TouristPlaceDatabase tpDb;
    ArrayList<TourPlace> tPlaces;
    private TextView viewTouristPlaces;
    private ListView tList;
    private String PLACE_ID;
    RecyclerView recyclerView;
    RecyclerView.Adapter placesAdapter;


    public void onItemClicked(int index){
     //        tPlaces.get(index).getId();
        Toast.makeText(this,"You Clicked"+tPlaces.get(index).getName(), Toast.LENGTH_SHORT).show();
    }
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_places);

        //generating the view
        recyclerView = findViewById(R.id.placesList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Putting data into adapter
        tPlaces = new ArrayList<TourPlace>();
        tpDb = new TouristPlaceDatabase(this);
        //getting all values
        tPlaces = tpDb.getAllTourPlace();
        if(tPlaces.size()<0){
           tPlaces = new ArrayList<TourPlace>();
            TourPlace tp = new TourPlace(1,"Murree The Hills","Murree near Islamabad","Murree is very beautifull place to visit. We have lot of hotels and activities to enjoy with",2000,"murree","hazara","33.90836","73.3903");
            tPlaces.add(tp);
        }
        placesAdapter = new UserPlacesAdapter(this,tPlaces);
        recyclerView.setAdapter(placesAdapter);

    }

}
