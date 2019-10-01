package com.example.zahoor.activities;

import adapters.UserPlacesAdapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zahoor.model.TourPlace;
import com.example.zahoor.sql.TouristPlaceDatabase;
import com.example.zahoor.sql.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.zahoor.activities.R.id.mainActivityLayout;


public class MainActivity extends AppCompatActivity {
    //List of Permission
    String[] appPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private static final int UNIQUE_REQUEST_CODE = 999;

    private Button button;
    private TextView registerLink;
    private TextView userEmail;
    private TextView userPassword;
    private Button loginBtn;
    private UserDatabaseHelper userDb;
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding click lister to button
        button = (Button) findViewById(R.id.addTourPlaceBtn);
        loginBtn = (Button) findViewById(R.id.buttonLogin);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userPassword = (TextView) findViewById(R.id.userPassword);
        registerLink = (TextView) findViewById(R.id.registerLink);

        //check all permission and if need then AppCompatActivity will request for permission
        if(checkAndRequestPermission()){
        //login Activity
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                userDb = new UserDatabaseHelper(v.getContext());
                if (!userDb.getUser(email,password)) {
                    message ="Login Failed";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                } else {
                    message ="Login successfully";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    if(email.equals("zahoor_ahmed143@hotmail.com")){
                        //Calling the add tour place activity
                        Intent intent = new Intent(v.getContext(),AdminDashboardActivity.class);
                        startActivity(intent);
                    }else{
                       //Calling the add tour place activity
                        Intent intent = new Intent(v.getContext(),UserViewPlacesActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        }
        //calling the register activity.
        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),RegisterActivity.class);
                startActivity(intent);
            }   // ends onClick
        });
        //button to see all the activities
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),UserViewPlacesActivity.class);
                startActivity(intent);
            }   // ends onClick
        });// setOnClickListener
    }
    private Boolean loggedIn(){
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        System.out.println("Email"+ email +" password" + password);
        return userDb.checkUser(email,password);
    }
    //getting the permission
    public boolean checkAndRequestPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for(String perm: appPermissions){
            if(ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(perm);
            }
        }
        //Now ask for the now granted permission
        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),UNIQUE_REQUEST_CODE);
            return false;
        }

        return true;
    }
    //OnPermissionResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == UNIQUE_REQUEST_CODE){
            HashMap<String,Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            //Getterh permission grant result
            for( int i=0; i<grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i],grantResults[i]);
                    deniedCount++;
                }
            }
            if(deniedCount == 0){
                Toast.makeText(this,"All Permission Granted inner Go Ahead",Toast.LENGTH_SHORT).show();
            }else{
                for(Map.Entry<String , Integer> entry: permissionResults.entrySet()){
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    //now use the shouldshownRequestPermission
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,permName)){
                        showDialog("","This App needs Camera, Location, Sotrage and otehr permission","Yes Grant",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                checkAndRequestPermission();
                            }
                        },"No Exit App", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        },false);

                    }else{
                        showDialog("","Allow All Permissions","Go to Settings",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //Open setting
                                Intent intent= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package",getPackageName(),null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        },"No Exit Application", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        },false);

                        break;
                    }
                }//ens for loop
            }
        }
    }
    //defining on alert method
    public AlertDialog showDialog(String title, String message, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnclick, boolean isCancelable){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
        dialog.setMessage(message);
        dialog.setPositiveButton(positiveLabel, positiveOnClick);
        dialog.setNegativeButton(negativeLabel, negativeOnclick);
        AlertDialog alert = dialog.create();
        alert.show();
        return alert;
    }
}
