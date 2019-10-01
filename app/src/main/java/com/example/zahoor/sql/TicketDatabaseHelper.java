package com.example.zahoor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.zahoor.model.Ticket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketDatabaseHelper {
    private MainDatabaseHelper mainDb;
    /**
     * Constructor
     *
     * @param context
     */
    public TicketDatabaseHelper(Context context)
    {
      mainDb = new MainDatabaseHelper(context);
    }

    public String addTicket(Ticket tkt) {
        String message="";
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_TICKET_NAME, tkt.getName());
        values.put(mainDb.COLUMN_TICKET_DESC, tkt.getDesc());
        values.put(mainDb.COLUMN_TICKET_PRICE, tkt.getPrice());
        values.put(mainDb.COLUMN_TICKET_TRAVELDATE, tkt.gettDate());
        values.put(mainDb.COLUMN_TICKET_VALIDITY, tkt.getValidity());

        // Inserting Row
        if(db.insert(mainDb.TABLE_TICKET, null, values)>0){
            message ="Inserted Successfully";
        }
        db.close();
        return message;
    }
    public ArrayList<Ticket> getAllTicket() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_TICKET_ID,
                mainDb.COLUMN_TICKET_NAME,
                mainDb.COLUMN_TICKET_DESC,
                mainDb.COLUMN_TICKET_PRICE,
                mainDb.COLUMN_TICKET_TRAVELDATE,
                mainDb.COLUMN_TICKET_VALIDITY
        };
        // sorting orders
        String sortOrder = mainDb.COLUMN_TICKET_ID + " ASC";
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the tour table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_TICKET, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ticket tkt = new Ticket();
                tkt.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_TICKET_ID)));
                tkt.setName(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TICKET_NAME)));
                tkt.setDesc(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TICKET_DESC)));
                tkt.setPrice(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TICKET_PRICE)));
                tkt.settDate(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TICKET_TRAVELDATE)));
                tkt.setValidity(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TICKET_VALIDITY)));

                // Adding user record to list
                ticketList.add(tkt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return ticketList;
    }
    /*
     *
     * This method to update user record
     *
     * @param rating
     */
    public void updateRating(Ticket tkt){
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_TICKET_NAME,tkt.getName());
        values.put(mainDb.COLUMN_TICKET_DESC,tkt.getDesc());
        values.put(mainDb.COLUMN_TICKET_PRICE,tkt.getPrice());
        values.put(mainDb.COLUMN_TICKET_TRAVELDATE,tkt.gettDate());
        values.put(mainDb.COLUMN_TICKET_VALIDITY,tkt.getValidity());

        // updating row
        db.update(mainDb.TABLE_TICKET, values, mainDb.COLUMN_TICKET_ID + " = ? ",
                new String[]{String.valueOf( tkt.getId())});

        db.close();
    }
    //getting the single Tour Place
    public Ticket getTicket(int id) {
        String query = "Select * FROM " + mainDb.TABLE_TICKET + " WHERE " + mainDb.COLUMN_TICKET_ID + "=" + "'"+id+"'";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Ticket tkt = new Ticket();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            tkt.setId(cursor.getInt(0));
            tkt.setName(cursor.getString(1));
            tkt.setDesc(cursor.getString(2));
            tkt.setPrice(cursor.getString(3));
            tkt.settDate(cursor.getString(4));
            tkt.setValidity(cursor.getString(5));

            cursor.close();
        } else {
            tkt = null;
        }
        db.close();
        return tkt;
    }

}



