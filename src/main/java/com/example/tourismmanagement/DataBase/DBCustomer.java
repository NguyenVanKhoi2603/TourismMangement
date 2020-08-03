package com.example.tourismmanagement.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.ProvinceModel;

import java.util.ArrayList;

public class DBCustomer {
    DBHelper dbHelper;

    public DBCustomer(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("customer_id", customerModel.getC_code());
        values.put("customer_name", customerModel.getC_fullname());
        values.put("customer_sex", customerModel.getC_sex());
        values.put("customer_numberphone", customerModel.getC_numberphone());
        values.put("customer_dayofbirth", customerModel.getC_dayofbirth());
        values.put("customer_gmail", customerModel.getC_gmail());
        values.put("customer_address", customerModel.getC_address());
        values.put("customer_avatar", customerModel.getImgavatar());
        db.insert("Customers", null, values);
    }

    public ArrayList<CustomerModel> getListCustomer() {
        ArrayList<CustomerModel> data = new ArrayList<>();
        String sql = "select * from Customers";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        do {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setC_code(cursor.getString(0));
            customerModel.setC_fullname(cursor.getString(1));
            customerModel.setC_sex(cursor.getString(2));
            customerModel.setC_dayofbirth(cursor.getString(3));
            customerModel.setC_numberphone(cursor.getString(4));
            customerModel.setC_gmail(cursor.getString(5));
            customerModel.setC_address(cursor.getString(6));
            customerModel.setImgavatar(cursor.getBlob(7));
            //customer_code text, customer_name text, customer_sex text, customer_dayofbirth text, customer_numberphone text, customer_gmail text, customer_address text
            data.add(customerModel);
        }
        while (cursor.moveToNext());
        return data;
    }

    public ArrayList<CustomerModel> getDataByCode(String code) {
        ArrayList<CustomerModel> data = new ArrayList<>();
        String sql = "Select * from Customers where customer_id = '" + code + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setC_code(cursor.getString(0));
                customerModel.setC_fullname(cursor.getString(1));
                customerModel.setC_sex(cursor.getString(2));
                customerModel.setC_dayofbirth(cursor.getString(3));
                customerModel.setC_numberphone(cursor.getString(4));
                customerModel.setC_gmail(cursor.getString(5));
                customerModel.setC_address(cursor.getString(6));
                customerModel.setImgavatar(cursor.getBlob(7));

                data.add(customerModel);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
        }
        return data;
    }

    public void deleteCustomer(String c_Code) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("province_code", province.getP_code());
//        values.put("province_name", province.getP_name());
//        values.put("province_regions", province.getP_regions());
        db.delete("Customers", "customer_id = '" + c_Code + "'", null);
        db.close();
    }

    public void updateCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("customer_id", customerModel.getC_code());
        values.put("customer_name", customerModel.getC_fullname());
        values.put("customer_sex", customerModel.getC_sex());
        values.put("customer_numberphone", customerModel.getC_numberphone());
        values.put("customer_dayofbirth", customerModel.getC_dayofbirth());
        values.put("customer_gmail", customerModel.getC_gmail());
        values.put("customer_address", customerModel.getC_address());
        values.put("customer_avatar", customerModel.getImgavatar());
        db.update("Customers", values, "customer_id ='" + customerModel.getC_code() + "'", null);
        db.close();
    }


}
