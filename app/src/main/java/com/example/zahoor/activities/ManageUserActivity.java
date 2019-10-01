package com.example.zahoor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zahoor.model.User;
import com.example.zahoor.sql.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import adapters.UsersAdapter;

public class ManageUserActivity extends AppCompatActivity implements UsersAdapter.ItemClicked{
    private UserDatabaseHelper userDb;
    ArrayList<User> users;
    private ListView userList;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        //setting the layout
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        //setting layout manager
        recyclerView.setLayoutManager(layoutManager);
        //putting data into the adapter
        users = new ArrayList<User>();
        userDb = new UserDatabaseHelper(this);
        users = userDb.getAllUser();
        System.out.println("Total users "+ users.size());
        rAdapter = new UsersAdapter(this,users);
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        final int placeId = users.get(index).getId();
        Toast.makeText(this,"User"+placeId+" tapped", Toast.LENGTH_SHORT).show();
    }
}
