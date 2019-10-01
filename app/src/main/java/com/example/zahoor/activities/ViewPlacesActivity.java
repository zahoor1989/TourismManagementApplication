package com.example.zahoor.activities;

import adapters.CustomTourAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.zahoor.sql.TouristPlaceDatabase;
import com.example.zahoor.model.TourPlace;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class ViewPlacesActivity extends AppCompatActivity implements CustomTourAdapter.ItemClicked {
    private TouristPlaceDatabase tpDb;
    ArrayList<TourPlace> tourPlaces;
    private TextView viewTouristPlaces;
    private ListView tourList;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    private String PLACE_ID;


    @Override
    public void onItemClicked(int index) {
        final int placeId = tourPlaces.get(index).getId();
        //Dialog box
        final AlertDialog.Builder userChoic = new AlertDialog.Builder(this);
        userChoic.setTitle("User Choice").setMessage("Please Select One?");
        //setting the positive String
        userChoic.setPositiveButton(R.string.viewActivity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //putting values in bundle
                Bundle bnd = new Bundle();
                //view Single Activity
                Intent singlePlace = new Intent(userChoic.getContext(), SingleTourPlaceActivity.class);
                bnd.putString("PLACE_ID", Integer.toString(placeId));
                singlePlace.putExtras(bnd);
                System.out.println("Sending place id " + placeId);
                startActivity(singlePlace);
            }
        });
        userChoic.setNegativeButton(R.string.deletePlace, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //crating the delete interface for torist place.
                tpDb = new TouristPlaceDatabase(userChoic.getContext());
                String message = tpDb.deletePlace(placeId);
                Toast.makeText(userChoic.getContext(),message, Toast.LENGTH_SHORT).show();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = userChoic.create();
        dialog.show();
        //Toast.makeText(this, "Place Name"+ tourPlaces.get(index).getName(), Toast.LENGTH_SHORT).show();

    }

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_places);
        // getAllTourPlaces();
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        //putting data into the adapter
        tourPlaces = new ArrayList<TourPlace>();
        tpDb = new TouristPlaceDatabase(this);
        tourPlaces = tpDb.getAllTourPlace();
        rAdapter = new CustomTourAdapter(this, tourPlaces);
        recyclerView.setAdapter(rAdapter);
    }
}
