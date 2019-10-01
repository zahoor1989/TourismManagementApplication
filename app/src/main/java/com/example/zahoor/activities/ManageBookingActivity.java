package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zahoor.model.Booking;
import com.example.zahoor.sql.BookingDatabaseHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.BookingAdapter;

public class ManageBookingActivity extends AppCompatActivity implements BookingAdapter.ItemClicked{
    private BookingDatabaseHelper bookingDb;
    ArrayList<Booking> bookings;
    private ListView bookingList;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);

        //getting list view ready
        //setting the layout
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        //setting layout manager
        recyclerView.setLayoutManager(layoutManager);
        //putting data into the adapter
        bookings = new ArrayList<Booking>();
        bookingDb = new BookingDatabaseHelper(this);
        bookings = bookingDb.getAllBooking();
        System.out.println("Total Booking Size "+ bookings.size());
        //if the bookings are less that 0
        if(bookings.size()==0){
            Booking bk = new Booking();
            bk.setId(1);
            bk.setCustomer_id(2);
            bk.setProduct_id(2);
            bk.setQuantity(2);
            bk.setTotal(4000);
            bk.setType("Tour Place");
            bk.setPayStatus("Paid");
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            bk.setCreatedAt(formatter.format(new Date(System.currentTimeMillis())));
            //Now add the booking object
            bookings.add(bk);
        }
        rAdapter = new BookingAdapter(this,bookings);
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        final int placeId = bookings.get(index).getId();
        Toast.makeText(this,"User"+placeId+" tapped", Toast.LENGTH_SHORT).show();
    }
}
