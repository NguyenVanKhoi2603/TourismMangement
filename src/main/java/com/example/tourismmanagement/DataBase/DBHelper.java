package com.example.tourismmanagement.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "SQLiteTourismManage", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlProvince = "Create table Provinces (province_id text PRIMARY KEY, province_name text, province_regions text)";
        sqLiteDatabase.execSQL(sqlProvince);

        String sqlDestination = "Create table Destinations (des_id text PRIMARY KEY, des_name text, des_address text, des_province text" +
                ", des_description text, des_image BLOB, FOREIGN KEY(des_province) REFERENCES Provinces(province_id) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(sqlDestination);

        String sqlCustomer = "Create table Customers (customer_id text PRIMARY KEY, customer_name text, customer_sex text" +
                ", customer_dayofbirth text, customer_numberphone text UNIQUE, customer_gmail text UNIQUE, customer_address text, customer_avatar BLOB)";
        sqLiteDatabase.execSQL(sqlCustomer);

        String sqlTours = "Create table Tours (tour_id text PRIMARY KEY, tour_name text, tour_destination text, tour_time text, tour_price integer" +
                ", tour_vehicle text, tour_departure text, tour_info text, FOREIGN KEY(tour_destination) REFERENCES Destinations(des_id) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(sqlTours);

        String sqlBooking = "Create table Booking (book_id text PRIMARY KEY, customer_id text, tour_id text" +
                ", book_start_day text, booking_status text,book_note text, FOREIGN KEY(tour_id) REFERENCES Tours(tour_id) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(sqlBooking);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}