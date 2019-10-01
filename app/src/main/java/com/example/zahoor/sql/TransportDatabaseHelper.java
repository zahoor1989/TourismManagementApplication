 package com.example.zahoor.sql;

 import android.content.ContentValues;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
 import com.example.zahoor.model.Transport;

 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;

 public class TransportDatabaseHelper{
     private MainDatabaseHelper mainDb;
     public TransportDatabaseHelper(Context context) {
         mainDb = new MainDatabaseHelper(context);
     }

     public String addTransport(Transport trt) {
         String message="";
         //creating current date and time
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         Date date = new Date();
         //Package table columns names
         SQLiteDatabase db = mainDb.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(mainDb.COLUMN_TRANSPORT_PROID, trt.getProduct_id());
         values.put(mainDb.COLUMN_TRANSPORT_TYPE, trt.getType());
         values.put(mainDb.COLUMN_TRANSPORT_VNAME, trt.getVehicleName());
         values.put(mainDb.COLUMN_TRANSPORT_PICKUP, trt.getPickup());
         values.put(mainDb.COLUMN_TRANSPORT_DROP, trt.getDrop());
         values.put(mainDb.COLUMN_TRANSPORT_PRICE, trt.getPrice());

         // Inserting Row
         if(db.insert(mainDb.TABLE_TRANSPORT, null, values)>0){
             message = "Inserted successfully";
         }
         db.close();
         return message;
     }
     public ArrayList<Transport> getAllTransport() {
         // array of columns to fetch
         String[] columns = {
                 mainDb.COLUMN_TRANSPORT_ID,
                 mainDb.COLUMN_TRANSPORT_PROID,
                 mainDb.COLUMN_TRANSPORT_TYPE,
                 mainDb.COLUMN_TRANSPORT_VNAME,
                 mainDb.COLUMN_TRANSPORT_PICKUP,
                 mainDb.COLUMN_TRANSPORT_DROP,
                 mainDb.COLUMN_TRANSPORT_PRICE
         };
         // sorting orders
         String sortOrder = mainDb.COLUMN_TRANSPORT_ID + " ASC";
         ArrayList<Transport> transportList = new ArrayList<Transport>();

         SQLiteDatabase db = mainDb.getReadableDatabase();

         // query the tour table
         /**
          * Here query function is used to fetch records from user table this function works like we use sql query.
          * SQL query equivalent to this query function is
          * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
          */
         Cursor cursor = db.query(mainDb.TABLE_TRANSPORT, //Table to query
                 columns,    //columns to return
                 null,        //columns for the WHERE clause
                 null,        //The values for the WHERE clause
                 null,       //group the rows
                 null,       //filter by row groups
                 sortOrder); //The sort order


         // Traversing through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
                 Transport tkt = new Transport();
                 tkt.setId(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_ID)));
                 tkt.setProduct_id(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_PROID)));
                 tkt.setType(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_TYPE)));
                 tkt.setVehicleName(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_VNAME)));
                 tkt.setPickup(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_PICKUP)));
                 tkt.setDrop(cursor.getString(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_DROP)));
                 tkt.setPrice(cursor.getInt(cursor.getColumnIndex(mainDb.COLUMN_TRANSPORT_PRICE)));

                 // Adding user record to list
                 transportList.add(tkt);
             } while (cursor.moveToNext());
         }
         cursor.close();
         db.close();

         // return transport list
         return transportList;
     }
     /*
      *
      * This method to update user record
      *
      * @param rating
      */
     public void updateTransport(Transport trt){
         //creating current date and time
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         Date date = new Date();

         SQLiteDatabase db =mainDb.getWritableDatabase();

         //Package table columns names
         ContentValues values = new ContentValues();
         values.put(mainDb.COLUMN_TRANSPORT_PROID,trt.getProduct_id());
         values.put(mainDb.COLUMN_TRANSPORT_TYPE,trt.getType());
         values.put(mainDb.COLUMN_TRANSPORT_VNAME,trt.getVehicleName());
         values.put(mainDb.COLUMN_TRANSPORT_PICKUP,trt.getPickup());
         values.put(mainDb.COLUMN_TRANSPORT_DROP,trt.getDrop());
         values.put(mainDb.COLUMN_TRANSPORT_PRICE,trt.getPrice());

         // updating row
         db.update(mainDb.TABLE_TRANSPORT, values, mainDb.COLUMN_TRANSPORT_ID + " = ? ",
                 new String[]{String.valueOf( trt.getId())});

         db.close();
     }
     //getting the single Tour Place
     public Transport getTransport(int id) {
         String query = "Select * FROM " + mainDb.TABLE_TRANSPORT + " WHERE " + mainDb.COLUMN_TRANSPORT_ID + "=" + "'"+id+"'";
         SQLiteDatabase db = mainDb.getWritableDatabase();
         Cursor cursor = db.rawQuery(query, null);
         Transport trt = new Transport();
         if (cursor.moveToFirst()) {
             cursor.moveToFirst();
             trt.setId(cursor.getInt(0));
             trt.setProduct_id(cursor.getInt(1));
             trt.setType(cursor.getString(2));
             trt.setVehicleName(cursor.getString(3));
             trt.setPickup(cursor.getString(4));
             trt.setDrop(cursor.getString(5));
             trt.setPrice(cursor.getInt(6));

             cursor.close();
         } else {
             trt = null;
         }
         db.close();
         return trt;
     }

 }





