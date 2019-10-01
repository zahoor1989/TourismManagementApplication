package com.example.zahoor.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zahoor.model.Ticket;
import com.example.zahoor.sql.TicketDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterTicketActivity extends AppCompatActivity {
    private EditText name;
    private EditText tickDesc;
    private EditText tickPrice;
    private EditText tickTravelDate;
    private EditText tickValidity;
    private Button addTicket;
    private Button viewTicket;
    private String message;
    private TicketDatabaseHelper ticketDb;
    private Ticket ticket;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ticket);
        //creating the objects
        ticketDb = new TicketDatabaseHelper(this);
        ticket = new Ticket();

        // view
        initViews();
    }
    private void initViews(){
        name = (EditText) findViewById(R.id.ticketName);
        tickDesc = (EditText) findViewById(R.id.ticketDesc);
        tickPrice = (EditText) findViewById(R.id.ticketPrice);
        tickTravelDate = (EditText) findViewById(R.id.travelDate);
        tickValidity = (EditText) findViewById(R.id.ticketValidity);
        addTicket = (Button) findViewById(R.id.addTicket);
        viewTicket = (Button) findViewById(R.id.viewTicket);
        addTicket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postDataToSQLite();
            }
        });
        viewTicket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Notify user that data has been saved successfully
                Intent intent = new Intent(v.getContext(),ManageTicketActivity.class);
                startActivity(intent);
            }
        });
        //creating on datepicker dialog
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        tickTravelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void  postDataToSQLite(){
        // new getting values from input fields and saving to db
        ticket.setName(name.getText().toString().trim());
        ticket.setDesc(tickDesc.getText().toString());
        ticket.setPrice(tickPrice.getText().toString().trim());
        ticket.settDate(tickTravelDate.getText().toString().trim());
        ticket.setValidity(tickValidity.getText().toString().trim());
        System.out.println("Saving: Name: "+name.getText().toString()+"\n Desc: "+ tickDesc.getText().toString()+"\nPrice: "+tickPrice.getText().toString().trim()+"\nTravle: "+tickTravelDate.getText().toString().trim()+"\nValidity:"+tickValidity.getText().toString().trim());
        message = ticketDb.addTicket(ticket);

        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tickTravelDate.setText(sdf.format(myCalendar.getTime()));
    }
}
