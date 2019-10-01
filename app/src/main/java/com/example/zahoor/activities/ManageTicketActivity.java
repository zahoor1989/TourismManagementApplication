package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zahoor.model.Ticket;
import com.example.zahoor.sql.TicketDatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapters.TicketAdapter;

public class ManageTicketActivity extends AppCompatActivity implements TicketAdapter.ItemClicked{
    private TicketDatabaseHelper ticketDb;
    ArrayList<Ticket> tickets;
    private ListView ticketList;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ticket);


        //getting list view ready
        //setting the layout
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        //setting layout manager
        recyclerView.setLayoutManager(layoutManager);
        //putting data into the adapter
        tickets = new ArrayList<Ticket>();
        ticketDb = new TicketDatabaseHelper(this);
        tickets = ticketDb.getAllTicket();
        System.out.println("Total Ticket Size "+ tickets.size());
        //if the bookings are less that 0
        if(tickets.size()==0){
            Ticket bk = new Ticket();
            bk.setId(1);
            bk.setName("Lahore to Islamabad");
            bk.setDesc("We provide comfortable travel service and affordable");
            bk.setPrice("550");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 5);
            //adding date after few days.
            bk.settDate(dateFormat.format(c.getTime()).toString());
            bk.setValidity("1 Month");

            //Now add the booking object
            tickets.add(bk);
        }
        rAdapter = new TicketAdapter(this,tickets);
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        final int placeId = tickets.get(index).getId();
        Toast.makeText(this,"Ticket "+placeId+" tapped", Toast.LENGTH_SHORT).show();
    }
}
