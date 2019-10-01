package com.example.zahoor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zahoor.model.Transport;
import com.example.zahoor.sql.TransportDatabaseHelper;

import static java.lang.Integer.parseInt;


public class RegisterTransportActivity extends Activity {
    private EditText productId;
    private EditText type;
    private EditText vName;
    private EditText pickup;
    private EditText drop;
    private EditText price;
    private Button addTransport;
    private Button viewTransport;
    private TransportDatabaseHelper transportDb;
    private Transport transport;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_transport);
        //creating object
        transportDb = new TransportDatabaseHelper(this);
        transport = new Transport();
        initViews();
    }
    private void initViews(){
        productId = (EditText) findViewById(R.id.productId);
        type = (EditText) findViewById(R.id.transportType);
        vName = (EditText) findViewById(R.id.vehicleName);
        pickup = (EditText) findViewById(R.id.pickUp);
        drop = (EditText) findViewById(R.id.dropUp);
        price = (EditText) findViewById(R.id.transportPrice);
        addTransport = (Button) findViewById(R.id.addTransportBtn);
        viewTransport = (Button) findViewById(R.id.viewTransport);
        addTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToSQLite();
            }
        });
        viewTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),ManageTransportActivity.class);
                startActivity(intent);
            }
        });

    }
    /**
     * This method is to initialize objects to be used
     */

    private void  postDataToSQLite(){
        // new getting values from input fields and saving to db
        if(!productId.getText().toString().isEmpty()){
            transport.setProduct_id(parseInt(productId.getText().toString().trim()));
            transport.setType(type.getText().toString().trim());
            transport.setVehicleName(vName.getText().toString().trim());
            transport.setPickup(pickup.getText().toString().trim());
            transport.setDrop(drop.getText().toString().trim());
            transport.setPrice(parseInt(price.getText().toString().trim()));
            message = transportDb.addTransport(transport);
        }else{
            message="Fill the form!";
        }
        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
    }
}
