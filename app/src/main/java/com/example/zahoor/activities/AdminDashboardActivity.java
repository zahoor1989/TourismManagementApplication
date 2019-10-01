package com.example.zahoor.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AdminDashboardActivity extends AppCompatActivity {
    private ImageButton manageBooking;
    private ImageButton manageUser;
    private ImageButton manageBus;
    private ImageButton manageTour;
    private ImageButton manageTicket;
    private ImageButton managePackage;
    private ImageButton manageHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        //calling methods
        intiViews();
    }
    //creating view
    public void intiViews(){
         manageBooking = (ImageButton) findViewById(R.id.manageBooking);
         manageUser = (ImageButton) findViewById(R.id.manageUser);
         manageBus = (ImageButton) findViewById(R.id.manageBus);
         manageTour = (ImageButton) findViewById(R.id.manageTour);
         manageTicket =(ImageButton) findViewById(R.id.manageTicket);
         managePackage = (ImageButton) findViewById(R.id.managePackage);
         manageHotel =(ImageButton) findViewById(R.id.manageHotel);
         //Creating navigation between activities
        manageBooking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),ManageBookingActivity.class);
                startActivity(intent);
            }
        });
        manageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),ManageUserActivity.class);
                startActivity(intent);
            }
        });
        manageBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),RegisterTransportActivity.class);
                startActivity(intent);
            }
        });
        manageTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),TouristPlace.class);
                startActivity(intent);
            }
        });
        manageTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),RegisterTicketActivity.class);
                startActivity(intent);
            }
        });
        managePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),RegisterPackageActivity.class);
                startActivity(intent);
            }
        });
        manageHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),RegisterHotelActivity.class);
                startActivity(intent);
            }
        });
    }
}
