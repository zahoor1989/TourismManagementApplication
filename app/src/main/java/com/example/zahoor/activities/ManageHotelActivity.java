package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zahoor.model.Hotel;
import com.example.zahoor.model.Ticket;
import com.example.zahoor.sql.HotelDatabaseHelper;
import com.example.zahoor.sql.TicketDatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapters.HotelAdapter;
import adapters.TicketAdapter;

public class ManageHotelActivity extends AppCompatActivity implements HotelAdapter.ItemClicked{
    private HotelDatabaseHelper hotelDb;
    ArrayList<Hotel> hotels;
    private ListView hotelList;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_hotel);


        //getting list view ready
        //setting the layout
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        //setting layout manager
        recyclerView.setLayoutManager(layoutManager);
        //putting data into the adapter
        hotels = new ArrayList<Hotel>();
        hotelDb = new HotelDatabaseHelper(this);
        hotels = hotelDb.getAllHotel();
        System.out.println("Total Hotel Size "+ hotels.size());
        //if the bookings are less that 0
        if(hotels.size()==0){
            Hotel bk = new Hotel();
            bk.setId(1);
            bk.setName("Marriat Hotels");
            bk.setPhone("009251458483");
            bk.setService("Five Start Hotel and Resturant");
            bk.setDesc("We provide comfortable travel service and affordable");
            bk.setPricePerNight("550");
            bk.setAddress("F8 Markaz Islamabad");
            bk.setLati("33.7327");
            bk.setLongi("73.0873");
            bk.setImages("bus");
            //Now add the booking object
            hotels.add(bk);
        }
        rAdapter = new HotelAdapter(this,hotels);
        recyclerView.setAdapter(rAdapter);


    }

    @Override
    public void onItemClicked(int index) {
        final String hotelName = hotels.get(index).getName();
        Toast.makeText(this,"Hotel "+hotelName+" tapped", Toast.LENGTH_SHORT).show();
    }
}
