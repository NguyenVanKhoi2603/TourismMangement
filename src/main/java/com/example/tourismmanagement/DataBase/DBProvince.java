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
        values.put("province_code", province.getP_code());
        values.put("province_name", province.getP_name());
        values.put("province_regions", province.getP_regions());
        db.insert("Provinces", null, values);
    }

    public ArrayList<ProvinceModel> getList() {
        ArrayList<ProvinceModel> data = new ArrayList<>();
        String sql = "select * from Provinces";
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

    public void deleteProvince(ProvinceModel province) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("province_code", province.getP_code());
        values.put("province_name", province.getP_name());
        values.put("province_regions", province.getP_regions());
        db.delete("Provinces", "province_code = '" + province.getP_code() + "'", null);
    }

}
