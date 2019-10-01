package com.example.zahoor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.zahoor.model.PackageModel;

import java.util.ArrayList;
import java.util.List;

public class PackageDatabaseHelper {
private MainDatabaseHelper mainDb;
    /**
     * Constructor
     *
     * @param context
     */
    public PackageDatabaseHelper(Context context) {
        mainDb = new MainDatabaseHelper(context);
    }


    /**
     * This method is to create package record
     *
     */
    public String addPackage(PackageModel pack) {
        String message = "";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_PACK_NAME, pack.getName());
        values.put(mainDb.COLUMN_PACK_DESC, pack.getDesc());
        values.put(mainDb.COLUMN_PACK_IMAGE, pack.getImage());
        values.put(mainDb.COLUMN_PACK_PRICE, pack.getPrice());


        // Inserting Row
        if(db.insert(mainDb.TABLE_PACK, null, values)>0){
            message = "Inserted Sucessully";
        }
        db.close();
        return message;
    }
    /**
     * This method is to fetch all TOUR PLACES and return the list of user records
     *
     * @return LIST
     */
    public List<PackageModel> getAllPackages() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_PACK_ID,
                mainDb.COLUMN_PACK_NAME,
                mainDb.COLUMN_PACK_DESC,
                mainDb.COLUMN_PACK_IMAGE,
                mainDb.COLUMN_PACK_PRICE
        };
        // sorting orders
        String sortOrder =
                mainDb.COLUMN_PACK_NAME + " ASC";
        List<PackageModel> packageList = new ArrayList<PackageModel>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the tour table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_PACK, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PackageModel pk = new PackageModel();
                pk.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_PACK_ID)));
                pk.setName(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_PACK_NAME)));
                pk.setDesc(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_PACK_DESC)));
                pk.setImage(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_PACK_IMAGE)));
                pk.setPrice(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_PACK_PRICE)));
                // Adding user record to list
                packageList.add(pk);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return packageList;
    }
    /*
     *
     * This method to update user record
     *
     * @param package
     */
    public void updatePackage(PackageModel pack){
        SQLiteDatabase db = mainDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_PACK_NAME,pack.getName());
        values.put(mainDb.COLUMN_PACK_DESC,pack.getDesc());
        values.put(mainDb.COLUMN_PACK_IMAGE,pack.getImage());
        values.put(mainDb.COLUMN_PACK_PRICE,pack.getPrice());



        // updating row
        db.update(mainDb.TABLE_PACK, values, mainDb.COLUMN_PACK_ID + " = ?",
                new String[]{String.valueOf(pack.getId())});
        db.close();
    }
    //getting the single Tour Place
    public PackageModel getPackage(int id) {
        String query = "Select * FROM " + mainDb.TABLE_PACK + " WHERE " + mainDb.COLUMN_PACK_ID + "=" + "'"+id+"'";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PackageModel pack = new PackageModel();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            pack.setId(cursor.getInt(0));
            pack.setName(cursor.getString(1));
            pack.setDesc(cursor.getString(2));
            pack.setImage(cursor.getString(3));
            pack.setPrice(cursor.getInt(4));
            cursor.close();
        } else {
           pack = null;
        }
        db.close();
        return pack;
    }
}
