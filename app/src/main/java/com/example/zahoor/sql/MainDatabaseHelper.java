package com.example.zahoor.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDatabaseHelper extends SQLiteOpenHelper {
    //database version
    public static final int DATABASE_VERSION =7;
    //Database name
    public static final String DATABASE_NAME = "TourManagement.db";

    //================Creating User Table=============================//
    // User table name
    public static final String TABLE_USER = "user";
    // User Table Columns names
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT);";
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    //================================================================//

    //========================Creating Tourists Table=================//
    //Tourist Place table
    public static final String TABLE_TOURPLACE ="tour_places";
    //Tourist table columns name
    public static final String COLUMN_TOUR_ID = "_id";
    public static final String COLUMN_TOUR_NAME = "tour_name";
    public static final String COLUMN_TOUR_PRICE = "tour_price";
    public static final String COLUMN_TOUR_ADD = "tour_add";
    public static final String COLUMN_TOUR_DESC = "tour_desc";
    public static final String COLUMN_TOUR_IMAGE = "tour_image";
    public static final String COLUMN_TOUR_VIDEO = "tour_video";
    public static final String COLUMN_TOUR_LAT = "latitude";
    public static final String COLUMN_TOUR_LONG = "longitude";
    public String CREATE_TOURPLACE_TABLE = "CREATE TABLE "+TABLE_TOURPLACE+" ("+COLUMN_TOUR_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_TOUR_NAME+" TEXT,"+COLUMN_TOUR_PRICE+" INTEGER,"+COLUMN_TOUR_ADD+" TEXT,"+COLUMN_TOUR_DESC+" TEXT,"+COLUMN_TOUR_IMAGE+" TEXT,"+COLUMN_TOUR_VIDEO+" TEXT,"+COLUMN_TOUR_LAT +" TEXT,"+ COLUMN_TOUR_LONG +" TEXT);";
    public String DROP_TOURPLACE_TABLE= "DROP TABLE IF EXISTS "+TABLE_TOURPLACE;
    //================================================================//

    //========================Creating Booking Table=================//
    //Package table
    public static final String TABLE_BOOKING="booking";
    //Package table columns names
    public static final String COLUMN_BOOKING_ID = "_id";
    public static final String COLUMN_BOOKING_PROID = "booking_proid";
    public static final String COLUMN_BOOKING_CID = "booking_cid";
    public static final String COLUMN_BOOKING_QUANTITY = "booking_quantity";
    public static final String COLUMN_BOOKING_TOTAL = "booking_total";
    public static final String COLUMN_BOOKING_TYPE = "booking_type";
    public static final String COLUMN_BOOKING_PAYSTATUS= "paystatus";
    public static final String COLUMN_BOOKING_DATE = "created_at";
    // create table sql query
    public String CREATE_BOOKING_TABLE ="CREATE TABLE " + TABLE_BOOKING +" ("+ COLUMN_BOOKING_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_BOOKING_PROID +" TEXT,"+ COLUMN_BOOKING_CID +" TEXT,"+ COLUMN_BOOKING_QUANTITY +" TEXT,"+ COLUMN_BOOKING_TOTAL + " INTEGER,"+ COLUMN_BOOKING_TYPE+" TEXT,"+ COLUMN_BOOKING_PAYSTATUS +" TEXT,"+ COLUMN_BOOKING_DATE +" INTEGER);";

    //drop if the table already exists.
    public String DROP_BOOKING_TABLE= " DROP TABLE IF EXISTS " + TABLE_BOOKING ;
    //================================================================//

    //========================Creating Hotel Table Table==============//
    //Package table
    public static final String TABLE_HOTEL="hotel";
    //Package table columns names
    public static final String COLUMN_HOTEL_ID = "_id";
    public static final String COLUMN_HOTEL_NAME = "name";
    public static final String COLUMN_HOTEL_ADDRESS = "address";
    public static final String COLUMN_HOTEL_PHONE = "phone";
    public static final String COLUMN_HOTEL_DESC = "description";
    public static final String COLUMN_HOTEL_SERVICE = "service";
    public static final String COLUMN_HOTEL_PRICE = "pricePerNight";
    public static final String COLUMN_HOTEL_IMAGE = "image";
    public static final String COLUMN_HOTEL_LAT = "latitude";
    public static final String COLUMN_HOTEL_LONG = "longitude";
    // create table sql query
    public String CREATE_HOTEL_TABLE ="CREATE TABLE "+TABLE_HOTEL+" ("+COLUMN_HOTEL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_HOTEL_NAME +" TEXT,"+COLUMN_HOTEL_ADDRESS+" TEXT,"+COLUMN_HOTEL_PHONE+ " TEXT,"+COLUMN_HOTEL_DESC+ " INTEGER,"+COLUMN_HOTEL_SERVICE + " TEXT,"+COLUMN_HOTEL_PRICE+" TEXT,"+COLUMN_HOTEL_IMAGE+" INTEGER,"+ COLUMN_HOTEL_LAT+" TEXT,"+COLUMN_HOTEL_LONG +" TEXT);";

    //drop if the table already exists.
    public String DROP_HOTEL_TABLE= "DROP TABLE IF EXISTS " + TABLE_HOTEL ;
    //================================================================//

    //========================Creating Package Table==================//
    //Package table
    public static final String TABLE_PACK="tourpackage";

    //Package table columns names
    public static final String COLUMN_PACK_ID = "_id";
    public static final String COLUMN_PACK_NAME = "package_name";
    public static final String COLUMN_PACK_DESC = "package_desc";
    public static final String COLUMN_PACK_IMAGE = "package_image";
    public static final String COLUMN_PACK_PRICE = "package_price";
    // create table sql query
    public String CREATE_PACKAGE_TABLE ="CREATE TABLE "+TABLE_PACK+" ("+COLUMN_PACK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_PACK_NAME + " TEXT,"+COLUMN_PACK_DESC + " TEXT,"+COLUMN_PACK_IMAGE+" TEXT,"+COLUMN_PACK_PRICE+" INTEGER);";
    public String DROP_PACKAGE_TABLE= "DROP TABLE IF EXISTS " + TABLE_PACK ;
    //================================================================//

    //========================Creating Rating Table===================//
    //Package table
    public static final String TABLE_RATING="rating";
    //Package table columns names
    public static final String COLUMN_RATING_ID = "_id";
    public static final String COLUMN_RATING_CID = "customer_id";
    public static final String COLUMN_RATING_VAL= "rating";
    public static final String COLUMN_RATING_PROID = "product_id";
    public static final String COLUMN_RATING_DATE = "createdAt";
    // create table sql query
    public String CREATE_RATING_TABLE ="CREATE TABLE "+TABLE_RATING+" ("+COLUMN_RATING_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_RATING_CID+" INTEGER,"+COLUMN_RATING_VAL+" INTEGER,"+COLUMN_RATING_PROID+" INTEGER,"+COLUMN_RATING_DATE+ " INTEGER);";

    //drop if the table already exists.
    public String DROP_RATING_TABLE= "DROP TABLE IF EXISTS " + TABLE_RATING ;

    //================================================================//

    //========================Creating Ticket Table===================//
    //Package table
    public static final String TABLE_TICKET="ticket";
    //Package table columns names
    public static final String COLUMN_TICKET_ID = "_id";
    public static final String COLUMN_TICKET_NAME = "name";
    public static final String COLUMN_TICKET_DESC= "description";
    public static final String COLUMN_TICKET_PRICE = "price";
    public static final String COLUMN_TICKET_TRAVELDATE = "tDate";
    public static final String COLUMN_TICKET_VALIDITY = "validity";
    // create table sql query
    public String CREATE_TICKET_TABLE ="CREATE TABLE "+TABLE_TICKET+" ("+ COLUMN_TICKET_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_TICKET_NAME+ " TEXT,"+COLUMN_TICKET_DESC+ " TEXT,"+COLUMN_TICKET_PRICE+" TEXT,"+COLUMN_TICKET_TRAVELDATE+" TEXT,"+COLUMN_TICKET_VALIDITY+" TEXT);";

    //drop if the table already exists.
    public String DROP_TICKET_TABLE= "DROP TABLE IF EXISTS " + TABLE_TICKET ;

    //================================================================//

    //========================Creating Transport Table================//
    //transport table
    public static final String TABLE_TRANSPORT = "transport";
    //Package table columns names
    public static final String COLUMN_TRANSPORT_ID = "_id";
    public static final String COLUMN_TRANSPORT_PROID = "product_id";
    public static final String COLUMN_TRANSPORT_TYPE = "type";
    public static final String COLUMN_TRANSPORT_VNAME = "vehicle_name";
    public static final String COLUMN_TRANSPORT_PICKUP = "pickup";
    public static final String COLUMN_TRANSPORT_DROP = "dropup";
    public static final String COLUMN_TRANSPORT_PRICE = "price";

    // create table sql query
    public String CREATE_TRANSPORT_TABLE ="CREATE TABLE "+TABLE_TRANSPORT+" ("+COLUMN_TRANSPORT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_TRANSPORT_PROID+" TEXT,"+COLUMN_TRANSPORT_TYPE+" TEXT,"+COLUMN_TRANSPORT_VNAME+" TEXT,"+COLUMN_TRANSPORT_PICKUP+" TEXT,"+COLUMN_TRANSPORT_DROP+" TEXT,"+COLUMN_TRANSPORT_PRICE+" TEXT);";

    //drop if the table already exists.
    public String DROP_TRANSPORT_TABLE= "DROP TABLE IF EXISTS " + TABLE_TRANSPORT ;
    //================================================================//

    //========================Creating Tourists Table=================//
    //================================================================//
    //creating constructor
    public MainDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TRANSPORT_TABLE);
        db.execSQL(CREATE_TICKET_TABLE);
        db.execSQL(CREATE_RATING_TABLE);
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
        db.execSQL(CREATE_TOURPLACE_TABLE);
        db.execSQL(CREATE_PACKAGE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //Drop User Table if exist
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_TRANSPORT_TABLE);
            db.execSQL(DROP_TICKET_TABLE);
            db.execSQL(DROP_RATING_TABLE);
            db.execSQL(DROP_HOTEL_TABLE);
            db.execSQL(DROP_BOOKING_TABLE);
            db.execSQL(DROP_TOURPLACE_TABLE);
            db.execSQL(DROP_PACKAGE_TABLE);
            // Create tables again
            onCreate(db);

    }
}
