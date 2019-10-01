package com.example.zahoor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.zahoor.sql.MainDatabaseHelper;


import com.example.zahoor.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper{

    private MainDatabaseHelper mainDb;
    private String message;
    /**
     * This method is to create user record
     *
     */
    public UserDatabaseHelper(Context context){
        mainDb = new MainDatabaseHelper(context);
    }
    public String addUser(User user) {
        try {
            SQLiteDatabase db = mainDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(mainDb.COLUMN_USER_NAME, user.getName());
            values.put(mainDb.COLUMN_USER_EMAIL, user.getEmail());
            values.put(mainDb.COLUMN_USER_PASSWORD, user.getPassword());

            // Inserting Row
            db.insert(mainDb.TABLE_USER, null, values);
            db.close();
            return message = "inserted successfully";
        } catch (Exception e) {
            return message = "Database not available and " + e.getMessage();
        }
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public ArrayList<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_USER_ID,
                mainDb.COLUMN_USER_EMAIL,
                mainDb.COLUMN_USER_NAME,
                mainDb.COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                mainDb.COLUMN_USER_NAME + " ASC";
        ArrayList<User> userList = new ArrayList<User>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = mainDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_USER_NAME, user.getName());
        values.put(mainDb.COLUMN_USER_EMAIL, user.getEmail());
        values.put(mainDb.COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(mainDb.TABLE_USER, values, mainDb.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = mainDb.getWritableDatabase();
        // delete user record by id
        db.delete(mainDb.TABLE_USER, mainDb.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_USER_ID
        };
        SQLiteDatabase db =mainDb.getReadableDatabase();

        // selection criteria
        String selection = mainDb.COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(mainDb.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_USER_ID
        };
        SQLiteDatabase db = mainDb.getReadableDatabase();
        // selection criteria
        String selection = mainDb.COLUMN_USER_EMAIL + " = ?" + " AND " + mainDb.COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(mainDb.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //removing the place
    public Boolean getUser(String email, String password) {
        //getting the instace of db
        SQLiteDatabase db = mainDb.getReadableDatabase();
        String select = "SELECT " + mainDb.COLUMN_USER_ID + " FROM " + mainDb.TABLE_USER + " WHERE " + mainDb.COLUMN_USER_EMAIL + " = ? AND " + mainDb.COLUMN_USER_PASSWORD + "= ?";
        Cursor cursor = db.rawQuery(select, new String[]{email, password});
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}