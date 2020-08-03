package com.example.tourismmanagement.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tourismmanagement.Model.ProvinceModel;

import java.util.ArrayList;

public class DBProvince {
    DBHelper dbHelper;

    public DBProvince(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addProvince(ProvinceModel province) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("province_id", province.getP_code());
        values.put("province_name", province.getP_name());
        values.put("province_regions", province.getP_regions());
        db.insert("Provinces", null, values);
    }

    public ArrayList<ProvinceModel> getList() {
        ArrayList<ProvinceModel> data = new ArrayList<>();
        String sql = "select * from Provinces ORDER BY province_id";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        do {
            ProvinceModel province = new ProvinceModel();
            province.setP_code(cursor.getString(0));
            province.setP_name(cursor.getString(1));
            province.setP_regions(cursor.getString(2));
            data.add(province);
        }
        while (cursor.moveToNext());
        return data;
    }


    public ArrayList<ProvinceModel> getDataByCode(String code)
    {
        ArrayList<ProvinceModel>data = new ArrayList<>();
        String sql = "Select * from Provinces where province_id = '"+ code +"'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                ProvinceModel provinceModel = new ProvinceModel();
                provinceModel.setP_code(cursor.getString(0));
                provinceModel.setP_name(cursor.getString(1));
                provinceModel.setP_regions(cursor.getString(2));
                data.add(provinceModel);
            }
            while (cursor.moveToNext());
        }
        catch (Exception ex)
        {

        }
        return data;
    }

    public ArrayList<ProvinceModel> getDataByName(String name)
    {
        ArrayList<ProvinceModel>data = new ArrayList<>();
        String sql = "Select * from Provinces where province_name = '"+ name +"'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                ProvinceModel provinceModel = new ProvinceModel();
                provinceModel.setP_code(cursor.getString(0));
                provinceModel.setP_name(cursor.getString(1));
                provinceModel.setP_regions(cursor.getString(2));
                data.add(provinceModel);
            }
            while (cursor.moveToNext());
        }
        catch (Exception ex)
        {
        }
        return data;
    }

    public void deleteProvince(String province) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("province_code", province.getP_code());
//        values.put("province_name", province.getP_name());
//        values.put("province_regions", province.getP_regions());
        db.delete("Provinces", "province_id = '" + province + "'", null);
        db.close();
    }

    public void updateProvince(ProvinceModel province) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ;
        values.put("province_name", province.getP_name());
        values.put("province_regions", province.getP_regions());
        db.update("Provinces", values,"province_id ='" + province.getP_code() + "'", null);
        //db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(student.getId()) });

        db.close();
    }


}
