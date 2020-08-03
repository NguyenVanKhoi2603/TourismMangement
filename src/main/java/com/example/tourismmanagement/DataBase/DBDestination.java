package com.example.tourismmanagement.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tourismmanagement.InterFace.Destination.Destination;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.DestinationModel;

import java.util.ArrayList;

public class DBDestination {
    DBHelper dbHelper;

    public DBDestination(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addDestination(DestinationModel destination) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("des_id", destination.getDes_code());
        values.put("des_name", destination.getDes_name());
        values.put("des_address", destination.getDes_address());
        values.put("des_province", destination.getDes_province());
        values.put("des_description", destination.getDes_description());
        values.put("des_image", destination.getDes_image());

        db.insert("Destinations", null, values);
    }
    //Destinations (des_code text, des_name text, des_address text, des_province, des_description, des_image)";

    public ArrayList<DestinationModel> getListDestination() {
        ArrayList<DestinationModel> data = new ArrayList<>();
        String sql = "select * from Destinations";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        do {
            DestinationModel destinationModel = new DestinationModel();
            destinationModel.setDes_code(cursor.getString(0));
            destinationModel.setDes_name(cursor.getString(1));
            destinationModel.setDes_address(cursor.getString(2));
            destinationModel.setDes_province(cursor.getString(3));
            destinationModel.setDes_description(cursor.getString(4));
            destinationModel.setDes_image(cursor.getBlob(5));
            data.add(destinationModel);
        }
        while (cursor.moveToNext());
        return data;
    }

    public ArrayList<DestinationModel> getDesByCode(String code) {
        ArrayList<DestinationModel> data = new ArrayList<>();
        String sql = "Select * from Destinations where des_id = '" + code + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                DestinationModel destinationModel = new DestinationModel();
                destinationModel.setDes_code(cursor.getString(0));
                destinationModel.setDes_name(cursor.getString(1));
                destinationModel.setDes_address(cursor.getString(2));
                destinationModel.setDes_province(cursor.getString(3));
                destinationModel.setDes_description(cursor.getString(4));
                destinationModel.setDes_image(cursor.getBlob(5));
                data.add(destinationModel);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
        }
        return data;
    }

    public ArrayList<DestinationModel> getDesByName(String name) {
        ArrayList<DestinationModel> data = new ArrayList<>();
        String sql = "Select * from Destinations where des_name = '" + name + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                DestinationModel destinationModel = new DestinationModel();
                destinationModel.setDes_code(cursor.getString(0));
                destinationModel.setDes_name(cursor.getString(1));
                destinationModel.setDes_address(cursor.getString(2));
                destinationModel.setDes_province(cursor.getString(3));
                destinationModel.setDes_description(cursor.getString(4));
                destinationModel.setDes_image(cursor.getBlob(5));
                data.add(destinationModel);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
        }
        return data;
    }

    public void deleteDestination(String code) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("Destinations", "des_id = '" + code + "'", null);
        db.close();
    }

    public void updateDestination(DestinationModel destinationModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("des_id", destinationModel.getDes_code());
        values.put("des_name", destinationModel.getDes_name());
        values.put("des_address", destinationModel.getDes_address());
        values.put("des_province", destinationModel.getDes_province());
        values.put("des_description", destinationModel.getDes_description());
        values.put("des_image", destinationModel.getDes_image());

        db.update("Destinations", values, "des_id ='" + destinationModel.getDes_code() + "'", null);
        db.close();
    }


}
