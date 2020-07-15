package com.example.tourismmanagement.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "SQLiteTourismManage", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCustomer = "Create table Customers (customer_code text, customer_name text, customer_sex text, customer_dayofbirth text, customer_numberphone text, customer_gmail text, customer_address text)";
        sqLiteDatabase.execSQL(sqlCustomer);
        String sqlProvince = "Create table Provinces (province_code text, province_name text, province_regions text)";
        sqLiteDatabase.execSQL(sqlProvince);
    }
    //p
    // Customers (customer_code text, customer_name text, customer_numberphone text, customer_dayofbirth text, customer_gmail text, customer_address text)
    // Provinces (province_code text, province_name text, province_regions text)
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}