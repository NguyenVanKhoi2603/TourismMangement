package com.example.tourismmanagement.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "SQLiteTourismManage", null, 1);
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String sqlProvince = "Create table Provinces (province_code text, province_name text, province_regions text)";
        sqLiteDatabase.execSQL(sqlProvince);

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

        String sqlCustomer = "Create table Customers (customer_code text, customer_name text, customer_sex text, customer_dayofbirth text, customer_numberphone text, customer_gmail text, customer_address text)";
        sqLiteDatabase.execSQL(sqlCustomer);
    }
    //p
    // Customers (customer_code text, customer_name text, customer_numberphone text, customer_dayofbirth text, customer_gmail text, customer_address text)
    // Provinces (province_code text, province_name text, province_regions text)
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}