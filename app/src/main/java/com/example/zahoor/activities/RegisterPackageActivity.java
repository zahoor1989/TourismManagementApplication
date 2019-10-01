package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zahoor.model.PackageModel;
import com.example.zahoor.sql.PackageDatabaseHelper;

public class RegisterPackageActivity extends AppCompatActivity {
    private EditText name;
    private EditText detail;
    private EditText image;
    private EditText price;
    private Button addPackage;
    private Button viewPackage;
    private PackageDatabaseHelper packDb;
    private PackageModel pack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_package);
        //making objects
        packDb = new PackageDatabaseHelper(this);
        pack = new PackageModel();
        initViews();
    }
    private void initViews(){
        name = (EditText) findViewById(R.id.packName);
        detail = (EditText) findViewById(R.id.packDetail);
        image = (EditText) findViewById(R.id.packImage);
        price = (EditText) findViewById(R.id.packPrice);
        addPackage = (Button) findViewById(R.id.addPackage);
        viewPackage = (Button) findViewById(R.id.viewPackage);

        addPackage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //saving data
                postDataToSQLite();
            }
        });
    }

    private void  postDataToSQLite(){
        String message="";
        // new getting values from input fields and saving to db
        pack.setName(name.getText().toString().trim());
        pack.setDesc(detail.getText().toString().trim());
        pack.setImage(image.getText().toString());
        pack.setPrice(Integer.parseInt(price.getText().toString().trim()));

        message = packDb.addPackage(pack);

        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
    }

}
