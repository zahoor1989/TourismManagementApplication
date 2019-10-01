package com.example.zahoor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.zahoor.model.TourPlace;
import com.example.zahoor.sql.MainDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TouristPlaceDatabase{
    private String message;
    private MainDatabaseHelper mainDb;
        /**
     * Constructor
     *
     * @param context
     */
    public TouristPlaceDatabase(Context context) {
        mainDb = new MainDatabaseHelper(context);
    }
    /**
     * This method is to create user record
     *
     * @param tourPlace
     */
    public String addTour(TourPlace tourPlace) {
        try {
            SQLiteDatabase db = mainDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(mainDb.COLUMN_TOUR_NAME, tourPlace.getName());
            values.put(mainDb.COLUMN_TOUR_PRICE, tourPlace.getPrice());
            values.put(mainDb.COLUMN_TOUR_ADD, tourPlace.getAdd());
            values.put(mainDb.COLUMN_TOUR_DESC, tourPlace.getDesc());
            values.put(mainDb.COLUMN_TOUR_IMAGE, tourPlace.getImage());
            values.put(mainDb.COLUMN_TOUR_VIDEO, tourPlace.getVideo());
            values.put(mainDb.COLUMN_TOUR_LAT,tourPlace.getLati());
            values.put(mainDb.COLUMN_TOUR_LONG,tourPlace.getLongi());

            // Inserting Row
            db.insert(mainDb.TABLE_TOURPLACE, null,values);
            db.close();
            return message = "inserted successfully";
        }catch(Exception e){
            return  message = "Database not available";
        }

    }
    /**
     * This method is to fetch all TOUR PLACES and return the list of user records
     *
     * @return LIST
     */
    public ArrayList<TourPlace> getAllTourPlace() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_TOUR_ID,
                mainDb.COLUMN_TOUR_NAME,
                mainDb.COLUMN_TOUR_PRICE,
                mainDb.COLUMN_TOUR_ADD,
                mainDb.COLUMN_TOUR_DESC,
                mainDb.COLUMN_TOUR_IMAGE,
                mainDb.COLUMN_TOUR_VIDEO,
                mainDb.COLUMN_TOUR_LAT,
                mainDb.COLUMN_TOUR_LONG
        };
        // sorting orders
        String sortOrder = mainDb.COLUMN_TOUR_NAME + " ASC";
        ArrayList<TourPlace> tourList = new ArrayList<TourPlace>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the tour table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_TOURPLACE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TourPlace tp = new TourPlace();
                tp.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_TOUR_ID)));
                tp.setName(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_NAME)));
                tp.setPrice(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_TOUR_PRICE)));
                tp.setAdd(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_ADD)));
                tp.setDesc(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_DESC)));
                tp.setImage(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_IMAGE)));
                tp.setVideo(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_VIDEO)));
                tp.setLati(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_LAT)));
                tp.setLongi(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TOUR_LONG)));

                // Adding user record to list
                tourList.add(tp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return tourList;
    }
    /*
    **
            * This method to update user record
     *
             * @param tour
     */
    public void updateTourPlace(TourPlace tourPlace) {
        SQLiteDatabase db = mainDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_TOUR_NAME, tourPlace.getName());
        values.put(mainDb.COLUMN_TOUR_PRICE, tourPlace.getPrice());
        values.put(mainDb.COLUMN_TOUR_ADD, tourPlace.getAdd());
        values.put(mainDb.COLUMN_TOUR_DESC, tourPlace.getDesc());
        values.put(mainDb.COLUMN_TOUR_IMAGE, tourPlace.getImage());
        values.put(mainDb.COLUMN_TOUR_VIDEO, tourPlace.getVideo());
        values.put(mainDb.COLUMN_TOUR_LAT,tourPlace.getLati());
        values.put(mainDb.COLUMN_TOUR_LONG,tourPlace.getLongi());

        // updating row
        db.update(mainDb.TABLE_TOURPLACE, values, mainDb.COLUMN_TOUR_ID + " = ?",
                new String[]{Integer.toString(tourPlace.getId())});
        db.close();
    }
    //getting the single Tour Place
    public TourPlace getTourPlace(int id) {
        String query = "SELECT * FROM " + mainDb.TABLE_TOURPLACE + " WHERE " + mainDb.COLUMN_TOUR_ID + "=" + "'"+id+"'";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TourPlace tourPlace;
        tourPlace = new TourPlace();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            tourPlace.setId(cursor.getInt(0));
            tourPlace.setName(cursor.getString(1));
            tourPlace.setPrice(cursor.getInt(2));
            tourPlace.setAdd(cursor.getString(3));
            tourPlace.setDesc(cursor.getString(4));
            tourPlace.setImage(cursor.getString(5));
            tourPlace.setVideo(cursor.getString(6));
            tourPlace.setLati(cursor.getString(7));
            tourPlace.setLongi(cursor.getString(8));
            cursor.close();
        } else {
            tourPlace = null;
        }
        db.close();
        return tourPlace;
    }

    //removing the place
    public String deletePlace(int id){
    //getting the instance of db
        SQLiteDatabase db = mainDb.getWritableDatabase();
        String WHERE = mainDb.COLUMN_TOUR_ID +" = ?";
        int del = db.delete(mainDb.TABLE_TOURPLACE,WHERE,new String[]{Integer.toString(id)});
        if(del>0){
            message = del + "Record Deleted Successfully";
        }else{
            message = "Operation Failed";
        }
        return message ;
    }
}
