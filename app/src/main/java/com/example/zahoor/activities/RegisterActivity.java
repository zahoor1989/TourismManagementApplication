package com.example.zahoor.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zahoor.model.User;
import com.example.zahoor.sql.UserDatabaseHelper;

import javax.crypto.Mac;

public class RegisterActivity extends AppCompatActivity {
    //creating variables
    private EditText userName;
    private EditText pass;
    private EditText confirmPass;
    private EditText userEmail;
    private Button regUser;
    private UserDatabaseHelper userDb;
    private User userModel;
    private String message;
    private String password;
    private String passCofirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //variables method
        initViews();
        initListener();
        initObjects();

    }
    private void initViews(){
    //creating variable with view
        userName = (EditText) findViewById(R.id.userName);
        userEmail = (EditText) findViewById(R.id.inputEmail);
        pass = (EditText) findViewById(R.id.inputPassword);
        confirmPass = (EditText) findViewById(R.id.InputConfirmPassword);
        regUser = (Button) findViewById(R.id.ButtonRegister);
    }
    private void initListener(){
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = pass.getText().toString().trim();
                passCofirm = confirmPass.getText().toString().trim();

                if(!userName.getText().toString().isEmpty()) {

                    if (password.equals(passCofirm)) {
                        // onclick save all the values of the input fields
                        //String emailGiven = userEmail.getText().toString().trim();
                        //if(userExists(emailGiven)) {
                        //  message = "User already exists!";
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        //}else {
                        message = postDataToSQLite();
                        //Notify user that data has been saved successfully
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        //if the user created successfully
                        Intent mainActivityIntent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(mainActivityIntent);
                        //}

                    } else {
                        message = "Please check password fields";
                        //Notify user that data has been saved successfully
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    message = "Fill the form!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }   // ends onClick
        });
    }
    private  void initObjects(){
        userDb = new UserDatabaseHelper(this);
        userModel = new User();
    }
    private String postDataToSQLite(){

            userModel.setName(userName.getText().toString().trim());
            userModel.setEmail(userEmail.getText().toString().trim());
            userModel.setPassword(pass.getText().toString().trim());
            return message = userDb.addUser(userModel);
    }
    private boolean userExists(String email){
        return userDb.checkUser(email);
    }
}
