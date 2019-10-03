package com.example.zahoor.activities;

import adapters.UserPlacesAdapter;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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
    RecyclerView.LayoutManager layoutManager;
    //var for drawer
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleActionBar;
    private NavigationView nv;

    public void onItemClicked(int index){
     //        tPlaces.get(index).getId();
        Toast.makeText(this,"You Clicked"+tPlaces.get(index).getName(), Toast.LENGTH_SHORT).show();
    }

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

        //generating drawer look and feel
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_user_view_places);
        toggleActionBar = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(toggleActionBar);
        toggleActionBar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("MyTitle");

        //navigation view
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(UserViewPlacesActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(UserViewPlacesActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mycart:
                        Toast.makeText(UserViewPlacesActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggleActionBar.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
