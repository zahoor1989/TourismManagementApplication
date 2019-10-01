package com.example.zahoor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.zahoor.model.Rating;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingDatabaseHelper {
    private MainDatabaseHelper mainDb;
    /**
     * Constructor
     *
     * @param context
     */
    public RatingDatabaseHelper(Context context) {

        mainDb = new MainDatabaseHelper(context);
    }

    /**
     * This method is to create package record
     *
     */
    public void addRating(Rating rt) {
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_RATING_CID, rt.getCustomer_id());
        values.put(mainDb.COLUMN_RATING_VAL, rt.getRat());
        values.put(mainDb.COLUMN_RATING_PROID, rt.getProduct_id());
        values.put(mainDb.COLUMN_RATING_DATE, dateFormat.format(date));

        // Inserting Row
        db.insert(mainDb.TABLE_RATING, null, values);
        db.close();
    }
    public List<Rating> getAllRating() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_RATING_ID,
                mainDb.COLUMN_RATING_CID,
                mainDb.COLUMN_RATING_VAL,
                mainDb.COLUMN_RATING_PROID,
                mainDb.COLUMN_RATING_DATE
        };
        // sorting orders
        String sortOrder =
                mainDb.COLUMN_RATING_ID + " ASC";
        List<Rating> ratingList = new ArrayList<Rating>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the tour table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_RATING, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Rating rt = new Rating();
                rt.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_RATING_ID)));
                rt.setCustomer_id(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_RATING_CID)));
                rt.setRat(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_RATING_VAL)));
                rt.setProduct_id(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_RATING_PROID)));
                rt.setCreatedAt(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_RATING_DATE)));

                // Adding user record to list
                ratingList.add(rt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return ratingList;
    }
    /*
     *
     * This method to update user record
     *
     * @param rating
     */
    public void updateRating(Rating rt){
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_RATING_VAL,rt.getRat());
        values.put(mainDb.COLUMN_RATING_DATE,dateFormat.format(date));

        // updating row
        db.update(mainDb.TABLE_RATING, values, mainDb.COLUMN_RATING_CID + " = ? AND "+ mainDb.COLUMN_RATING_PROID + " = ?",
                new String[]{String.valueOf( rt.getCustomer_id()) , String.valueOf(rt.getProduct_id())});

        db.close();
    }
    //getting the single Tour Place
    public Rating getRating(int id) {
        String query = "Select * FROM " + mainDb.TABLE_RATING + " WHERE " + mainDb.COLUMN_RATING_PROID + "=" + "'"+id+"'";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Rating rt = new Rating();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            rt.setId(cursor.getInt(0));
            rt.setCustomer_id(cursor.getInt(1));
            rt.setRat(cursor.getInt(2));
            rt.setProduct_id(cursor.getInt(3));
            rt.setCreatedAt(cursor.getString(4));

            cursor.close();
        } else {
            rt = null;
        }
        db.close();
        return rt;
    }

}

