package com.example.zahoor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.zahoor.model.Hotel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HotelDatabaseHelper {
  private MainDatabaseHelper mainDb;
    /**
     * Constructor
     *
     * @param context
     */
    public HotelDatabaseHelper(Context context) {
        mainDb = new MainDatabaseHelper(context);
    }

    /**
     * This method is to create package record
     *
     */
    public String addHotel(Hotel ht) {
        String message="";
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_HOTEL_NAME, ht.getName());
        values.put(mainDb.COLUMN_HOTEL_ADDRESS, ht.getAddress());
        values.put(mainDb.COLUMN_HOTEL_PHONE, ht.getPhone());
        values.put(mainDb.COLUMN_HOTEL_DESC, ht.getDesc());
        values.put(mainDb.COLUMN_HOTEL_SERVICE, ht.getService());
        values.put(mainDb.COLUMN_HOTEL_PRICE, ht.getPricePerNight());
        values.put(mainDb.COLUMN_HOTEL_IMAGE, ht.getImages());
        values.put(mainDb.COLUMN_HOTEL_LAT, ht.getLati());
        values.put(mainDb.COLUMN_HOTEL_LAT, ht.getLongi());
        // Inserting Row
        if(db.insert(mainDb.TABLE_HOTEL, null, values)>0){
            message="Inserted Successfully";
        }
        db.close();
        return message;
    }
    public ArrayList<Hotel> getAllHotel() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_HOTEL_ID,
                mainDb.COLUMN_HOTEL_NAME,
                mainDb.COLUMN_HOTEL_ADDRESS,
                mainDb.COLUMN_HOTEL_PHONE,
                mainDb.COLUMN_HOTEL_DESC,
                mainDb.COLUMN_HOTEL_SERVICE,
                mainDb.COLUMN_HOTEL_PRICE,
                mainDb.COLUMN_HOTEL_IMAGE,
                mainDb.COLUMN_HOTEL_LAT,
                mainDb.COLUMN_HOTEL_LONG

        };
        // sorting orders
        String sortOrder =
                mainDb.COLUMN_HOTEL_ID + " ASC";
        ArrayList<Hotel> hotelList = new ArrayList<Hotel>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the tour table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_HOTEL, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hotel ht = new Hotel();
                ht.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_ID)));
                ht.setName(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_NAME)));
                ht.setAddress(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_ADDRESS)));
                ht.setPhone(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_PHONE)));
                ht.setDesc(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_DESC)));
                ht.setService(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_SERVICE)));
                ht.setPricePerNight(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_PRICE)));
                ht.setImages(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_IMAGE)));
                ht.setLati(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_LAT)));
                ht.setLongi(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_HOTEL_LONG)));
                // Adding user record to list
                hotelList.add(ht);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return hotelList;
    }
    /*
     *
     * This method to update user record
     *
     * @param package
     */
    public void updateBooking(Hotel hk){
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_HOTEL_ID,hk.getId());
        values.put(mainDb.COLUMN_HOTEL_NAME,hk.getName());
        values.put(mainDb.COLUMN_HOTEL_ADDRESS,hk.getAddress());
        values.put(mainDb.COLUMN_HOTEL_PHONE,hk.getPhone());
        values.put(mainDb.COLUMN_HOTEL_DESC,hk.getDesc());
        values.put(mainDb.COLUMN_HOTEL_SERVICE,hk.getService());
        values.put(mainDb.COLUMN_HOTEL_PRICE,hk.getPricePerNight());
        values.put(mainDb.COLUMN_HOTEL_IMAGE,hk.getImages());
        values.put(mainDb.COLUMN_HOTEL_LAT,hk.getLati());
        values.put(mainDb.COLUMN_HOTEL_LONG,hk.getLongi());

        // updating row
        db.update(mainDb.TABLE_HOTEL, values, mainDb.COLUMN_HOTEL_ID + " = ?",
                new String[]{String.valueOf(hk.getId())});
        db.close();
    }
    //getting the single Tour Place
    public Hotel getHotel(int id) {
        String query = "Select * FROM " + mainDb.TABLE_HOTEL + " WHERE " + mainDb.COLUMN_HOTEL_ID + "=" + "'"+id+"'";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Hotel ht = new Hotel();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            ht.setId(cursor.getInt(0));
            ht.setName(cursor.getString(1));
            ht.setAddress(cursor.getString(2));
            ht.setPhone(cursor.getString(3));
            ht.setDesc(cursor.getString(4));
            ht.setService(cursor.getString(5));
            ht.setPricePerNight(cursor.getString(6));
            ht.setImages(cursor.getString(7));
            ht.setLati(cursor.getString(8));
            ht.setLongi(cursor.getString(9));

            cursor.close();
        } else {
            ht = null;
        }
        db.close();
        return ht;
    }

}

