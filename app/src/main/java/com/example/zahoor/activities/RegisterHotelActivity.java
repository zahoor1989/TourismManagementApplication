package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zahoor.model.Hotel;
import com.example.zahoor.sql.HotelDatabaseHelper;

public class RegisterHotelActivity extends AppCompatActivity {
    private EditText hotelName;
    private EditText hotelAdd;
    private EditText hotelPhone;
    private EditText hotelDesc;
    private EditText hotelService;
    private EditText hotelPrice;
    private EditText hotelImage;
    private Button addHotel;
    private Button viewHotel;
    private HotelDatabaseHelper hotelDb;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hotel);
        //creating object
        hotelDb = new HotelDatabaseHelper(this);
        hotel = new Hotel();
        // views
        initViews();
    }
    private void initViews() {
         hotelName = (EditText) findViewById(R.id.hotelName);
         hotelAdd =(EditText) findViewById(R.id.hotelAddress);
         hotelPhone =(EditText) findViewById(R.id.hotelPhone);
         hotelDesc =(EditText) findViewById(R.id.hotelDesc);
         hotelService =(EditText) findViewById(R.id.hotelService);
         hotelPrice =(EditText) findViewById(R.id.hotelPrice);
         hotelImage =(EditText) findViewById(R.id.hotelImage);
         addHotel =(Button) findViewById(R.id.addHotelBtn);
         viewHotel =(Button) findViewById(R.id.viewHotelBtn);
         //add hotel listener
        addHotel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //save data
                postDataToSQLite();
            }
        });
    }
    private void  postDataToSQLite(){
        String message = "";
        // new getting values from input fields and saving to db
        hotel.setName(hotelName.getText().toString().trim());
        hotel.setAddress(hotelAdd.getText().toString());
        hotel.setPhone(hotelPhone.getText().toString().trim());
        hotel.setDesc(hotelDesc.getText().toString().trim());
        hotel.setService(hotelService.getText().toString().trim());
        hotel.setPricePerNight(hotelPrice.getText().toString().trim());
        hotel.setImages(hotelImage.getText().toString().trim());
        message = hotelDb.addHotel(hotel);

        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
