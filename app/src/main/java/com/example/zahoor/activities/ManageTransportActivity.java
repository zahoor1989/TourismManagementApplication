package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.zahoor.activities.R;
import com.example.zahoor.model.Transport;
import com.example.zahoor.sql.TransportDatabaseHelper;

import java.util.ArrayList;

import adapters.TransportAdapter;

public class ManageTransportActivity extends AppCompatActivity implements TransportAdapter.ItemClicked{
    private TransportDatabaseHelper trpDb;
    ArrayList<Transport> transports;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transport);

        //get list view and layout
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //setting list and data
        transports = new ArrayList<Transport>();
        trpDb = new TransportDatabaseHelper(this);

        //Now setting the db values in arraylist
        transports = trpDb.getAllTransport();
        //check if transports have less that 0
        if(transports.size()==0){
            Transport trp = new Transport();
            trp.setId(1);
            trp.setProduct_id(3242344); //registration no
            trp.setVehicleName("Pakistan Bus Service");
            trp.setType("Bus");
            trp.setPickup("Sarai Alamgir");
            trp.setDrop("Rawalapindi, Faiza Abad");
            trp.setPrice(1000);
            transports.add(trp);
        }

        rAdapter = new TransportAdapter(this,transports);
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        final int registration = transports.get(index).getProduct_id();
        Toast.makeText(this,"Transport "+registration+" tapped", Toast.LENGTH_SHORT).show();
    }
}
