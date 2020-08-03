package com.example.tourismmanagement.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tourismmanagement.Model.DestinationModel;
import com.example.tourismmanagement.Model.TourModel;

import java.util.ArrayList;

public class DBTours {
    DBHelper dbHelper;

    public DBTours(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addTour(TourModel tourModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tour_id", tourModel.getTour_id());
        values.put("tour_name", tourModel.getTour_name());
        values.put("tour_destination", tourModel.getTour_destination());
        values.put("tour_time", tourModel.getTour_time());
        values.put("tour_price", tourModel.getTour_price());
        values.put("tour_vehicle", tourModel.getTour_vehicle());
        values.put("tour_departure", tourModel.getTour_departure());
        values.put("tour_info", tourModel.getTour_info());
        db.insert("Tours", null, values);
    }
    //Tours (tour_id text PRIMARY KEY, tour_name text, tour_destination text, tour_time text, tour_price integer" +
    //        ", tour_vehicle text, tour_departure text, tour_info text)";

    public ArrayList<TourModel> getListTour() {
        ArrayList<TourModel> data = new ArrayList<>();
        String sql = "select * from Tours";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        do {
            TourModel tourModel = new TourModel();
            tourModel.setTour_id(cursor.getString(0));
            tourModel.setTour_name(cursor.getString(1));
            tourModel.setTour_destination(cursor.getString(2));
            tourModel.setTour_time(cursor.getString(3));
            tourModel.setTour_price(cursor.getInt(4));
            tourModel.setTour_vehicle(cursor.getString(5));
            tourModel.setTour_departure(cursor.getString(6));
            tourModel.setTour_info(cursor.getString(7));
            data.add(tourModel);
        }
        while (cursor.moveToNext());
        return data;
    }

    public ArrayList<TourModel> getTourByCode(String code) {
        ArrayList<TourModel> data = new ArrayList<>();
        String sql = "Select * from Tours where tour_id = '" + code + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                TourModel tourModel = new TourModel();
                tourModel.setTour_id(cursor.getString(0));
                tourModel.setTour_name(cursor.getString(1));
                tourModel.setTour_destination(cursor.getString(2));
                tourModel.setTour_time(cursor.getString(3));
                tourModel.setTour_price(cursor.getInt(4));
                tourModel.setTour_vehicle(cursor.getString(5));
                tourModel.setTour_departure(cursor.getString(6));
                tourModel.setTour_info(cursor.getString(7));
                data.add(tourModel);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
        }
        return data;
    }

    public void deleteDestination(String code) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("Tours", "tour_id = '" + code + "'", null);
        db.close();
    }

    public void updateTour(TourModel tourModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tour_id", tourModel.getTour_id());
        values.put("tour_name", tourModel.getTour_name());
        values.put("tour_destination", tourModel.getTour_destination());
        values.put("tour_time", tourModel.getTour_time());
        values.put("tour_price", tourModel.getTour_price());
        values.put("tour_vehicle", tourModel.getTour_vehicle());
        values.put("tour_departure", tourModel.getTour_departure());
        values.put("tour_info", tourModel.getTour_info());
        db.update("Tours", values, "tour_id ='" + tourModel.getTour_id() + "'", null);
        db.close();
    }


}
