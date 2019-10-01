package com.example.zahoor.sql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.zahoor.model.Booking;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDatabaseHelper {
    private MainDatabaseHelper mainDb;



    /**
     * Constructor
     *
     * @param context
     */
    public BookingDatabaseHelper(Context context) {
        mainDb = new MainDatabaseHelper(context);
    }

    /**
     * This method is to create package record
     *
     */
    public void addBooking(Booking book) {
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_BOOKING_PROID, book.getProduct_id());
        values.put(mainDb.COLUMN_BOOKING_CID, book.getCustomer_id());
        values.put(mainDb.COLUMN_BOOKING_QUANTITY, book.getQuantity());
        values.put(mainDb.COLUMN_BOOKING_TOTAL, book.getTotal());
        values.put(mainDb.COLUMN_BOOKING_TYPE, book.getType());
        values.put(mainDb.COLUMN_BOOKING_PAYSTATUS, book.getPayStatus());
        values.put(mainDb.COLUMN_BOOKING_DATE,dateFormat.format(date));

        // Inserting Row
        db.insert(mainDb.TABLE_BOOKING, null, values);
        db.close();
    }
    public ArrayList<Booking> getAllBooking() {
        // array of columns to fetch
        String[] columns = {
                mainDb.COLUMN_BOOKING_ID,
                mainDb.COLUMN_BOOKING_PROID,
                mainDb.COLUMN_BOOKING_CID,
                mainDb.COLUMN_BOOKING_QUANTITY,
                mainDb.COLUMN_BOOKING_TOTAL,
                mainDb.COLUMN_BOOKING_TYPE,
                mainDb.COLUMN_BOOKING_PAYSTATUS,
                mainDb.COLUMN_BOOKING_DATE,
        };
        // sorting orders
        String sortOrder =
                mainDb.COLUMN_BOOKING_ID + " ASC";
        ArrayList<Booking> bookingList = new ArrayList<Booking>();

        SQLiteDatabase db = mainDb.getReadableDatabase();

        // query the tour table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(mainDb.TABLE_BOOKING, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Booking bk = new Booking();
                bk.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_ID)));
                bk.setProduct_id(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_PROID)));
                bk.setCustomer_id(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_CID)));
                bk.setQuantity(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_QUANTITY)));
                bk.setTotal(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_TOTAL)));
                bk.setPayStatus(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_TYPE)));
                bk.setPayStatus(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_PAYSTATUS)));
                bk.setCreatedAt(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_BOOKING_DATE)));
                // Adding user record to list
                bookingList.add(bk);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return bookingList;
    }
    /*
     *
     * This method to update user record
     *
     * @param package
     */
    public void updateBooking(Booking bk){
        //creating current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = mainDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mainDb.COLUMN_BOOKING_PROID,bk.getProduct_id());
        values.put(mainDb.COLUMN_BOOKING_CID,bk.getCustomer_id());
        values.put(mainDb.COLUMN_BOOKING_QUANTITY,bk.getQuantity());
        values.put(mainDb.COLUMN_BOOKING_TOTAL,bk.getTotal());
        values.put(mainDb.COLUMN_BOOKING_TYPE,bk.getType());
        values.put(mainDb.COLUMN_BOOKING_PAYSTATUS,bk.getPayStatus());
        values.put(mainDb.COLUMN_BOOKING_DATE,dateFormat.format(date));

        // updating row
        db.update(mainDb.TABLE_BOOKING, values, mainDb.COLUMN_BOOKING_ID + " = ?",
                new String[]{String.valueOf(bk.getId())});
        db.close();
    }
    //getting the single Tour Place
    public Booking getBooking(int id) {
        String query =" SELECT * FROM " + mainDb.TABLE_BOOKING + " WHERE " + mainDb.COLUMN_BOOKING_ID + "=" + "'"+id+"'";
        SQLiteDatabase db = mainDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
            Booking bk = new Booking();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            bk.setId(cursor.getInt(0));
            bk.setProduct_id(cursor.getInt(1));
            bk.setCustomer_id(cursor.getInt(2));
            bk.setQuantity(cursor.getInt(3));
            bk.setTotal(cursor.getInt(4));
            bk.setType(cursor.getString(5));
            bk.setPayStatus(cursor.getString(6));
            bk.setCreatedAt(cursor.getString(7));

            cursor.close();
        } else {
            bk = null;
        }
        db.close();
        return bk;
    }

}
